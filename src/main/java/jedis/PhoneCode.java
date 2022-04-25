package jedis;

/**
 * @Author Mi
 * @Date 2022/4/18 13:39
 * @Version 1.0
 */


import redis.clients.jedis.Jedis;

import java.util.Random;

/**
 *   模拟根据手机号生成验证码
 */
public class PhoneCode {
    public static void main(String[] args) {

        //获取验证码
        String phoneCode = getPhoneCode("13232492505");
        System.out.println("输入验证码");
        //验证验证码
        getRedisCode("13232492505", phoneCode);

    }


    //生成验证码
    public static String getCode(){
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();
        for (int i =0 ; i < 6 ; i++){
            buffer.append(random.nextInt(10));
        }
        return buffer.toString();
    }


    //将验证码保存到redis中
    public static String verifyCode(String phone,String code){
        Jedis jedis = new Jedis("192.168.78.132",6379);
        //发送次数
        String countKey = phone + ":count";
        //验证码的key
        String codeKey = phone + ":code";

        //获取发送次数
        String s = jedis.get(countKey);
        if (s==null){
            jedis.setex(countKey,24*60*60,"1");
        }else if (Integer.parseInt(s) <=2){
            //发送次数+1
            jedis.incr(countKey);
        }else {
            jedis.close();
            return null;
        }
        //将验证码放到redis中
        jedis.setex(codeKey,120,code);
        jedis.close();
        return code;
    }

    //模拟第一步获取验证码
    public static String getPhoneCode(String phone){
        if (phone==null){
            return null;
        }
        //获取验证码
        String code = getCode();
        //将验证码保存到redis中并发送给用户
        String flag = verifyCode(phone, code);
        if (flag!=null){
            System.out.println("验证码已发送");
            return flag;
        }else {
            System.out.println("次数上限");
            return null;
        }
    }


    //校验
    public static boolean getRedisCode(String phone,String code){
        Jedis jedis = new Jedis("192.168.78.132",6379);
        String codeKey = phone + ":code";
        String s = jedis.get(codeKey);
        if (s.equals(code)){
            System.out.println("正确");
            jedis.close();
            return true;
        }else{
            System.out.println("验证码不正确");
            jedis.close();
            return false;
        }
    }





}
