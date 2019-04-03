package com.hwx.viney.oneUtils;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @program:paotuan
 * @author:one
 * @creatTime:2018/12/05
 **/

@Component
public class ArrayUtil {
    /**
     * 将 逗号分割的 数组 转换成 int数组
     * @param array
     * @return
     */
    public int[] stringToIntArray(String array){
        array = array.substring(0,array.length()-1);    //去掉最后一个，
        array = array.substring(1, array.length());     //去掉第一个，
        String[] arrayList = array.split(",");
        int[] arry = new int[arrayList.length];
        for(int i=0;i<arrayList.length;i++){
            arry[i] = Integer.parseInt(arrayList[i]);
        }
        return arry;
    }

    /**
     * 将 逗号分割的 数组 转换成 String数组
     * @param array     (,"1","1","3","5","6",) or ("1","1","3","5","6")
     * @return          {"1","1","3","5","6"}
     */
    public String[] stringToStringArray(String array){
        if(array.substring(0,1).equals(",")){
            array = array.substring(1, array.length());     //去掉第一个，
        }
        if(array.substring(array.length()-1).equals(",")){
            array = array.substring(0,array.length()-1);    //去掉最后一个，
        }
        String[] arrayList = array.split(",");
        String[] arry = new String[arrayList.length];
        for(int i=0;i<arrayList.length;i++){
            arry[i] = arrayList[i];
        }
        return arry;
    }

    /**
     * 删除Int数组中其中一个元素
     * @param arrayInt      {1,2,3,4,5,6}
     * @param removeIndex   3
     * @return              {1,2,3,5,6}
     */
    public int[] removeInt(int[] arrayInt,int removeIndex) {
        int[] newInt = new int[arrayInt.length-1];
        for(int i = 0;i<removeIndex;i++){
            newInt[i] = arrayInt[i];
        }
        for(int i = removeIndex;i < arrayInt.length - 1;i++){
            newInt[i] = arrayInt[i+1];
        }
        return newInt;
    }

    /**
     * 删除String数组中其中一个元素
     * @param arrayStr      {"1","2","3","4","5","6"}
     * @param removeIndex   3
     * @return              {"1","2","3","5","6"}
     */
    public String[] removeString(String[] arrayStr,int removeIndex) {
        String[] newStr = new String[arrayStr.length-1];
        for(int i = 0;i<removeIndex;i++){
            newStr[i] = arrayStr[i];
        }
        for(int i = removeIndex;i < arrayStr.length - 1;i++){
            newStr[i] = arrayStr[i+1];
        }
        return newStr;
    }

    /**
     *  找到 before 在 after 中缺少的字符串
     * @param before    {"1","2","3","4"}
     * @param after     {"0","3","2"}
     * @return          {"1","4"}
     */
    public String[] findLackStr(String[] before,String[] after){
        List<String> lackList = new ArrayList<>();
        for(int i=0; i<before.length; i++){
            boolean flag = false;
            for(int j=0; j<after.length; j++){
                if(before[i].equals(after[j])){
                    flag = true;
                    break;
                }
            }
            if(!flag){
                //找到缺少的字符串
                lackList.add(before[i]);
            }
        }
        return lackList.toArray(new String[lackList.size()]);
    }

    /**
     * 去除数组中空串
     * @param strArray  {"1","2","2",null,null}
     * @return          {"1","2","2"}
     */
    public String[] removeArrayEmptyTextBackNewArray(String[] strArray) {
        List<String> strList= Arrays.asList(strArray);
        List<String> strListNew=new ArrayList<>();
        for (int i = 0; i <strList.size(); i++) {
            if (strList.get(i)!=null&&!strList.get(i).equals("")){
                strListNew.add(strList.get(i));            }
        }
        String[] strNewArray = strListNew.toArray(new String[strListNew.size()]);
        return  strNewArray;
    }
}
