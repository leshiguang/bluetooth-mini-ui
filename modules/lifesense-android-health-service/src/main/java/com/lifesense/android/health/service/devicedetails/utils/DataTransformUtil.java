package com.lifesense.android.health.service.devicedetails.utils;


import com.lifesense.android.ble.device.band.model.config.Call;
import com.lifesense.android.ble.device.band.model.config.DialPlate;
import com.lifesense.android.ble.device.band.model.config.Page;
import com.lifesense.android.ble.device.band.model.enums.Day;
import com.lifesense.android.ble.device.band.model.enums.VibrationMode;

/**
 * Create by qwerty
 * Create on 2021/1/4
 **/
public class DataTransformUtil {
    public static String getDialPlateImageUrlByType(DialPlate.DialPlateType dialPlateType) {
        return "";
    }

    public static String getTimeFormatTextByType(int type) {
        if(type == 0) {
            return "24小时制";
        } else {
            return "12小时制";
        }
    }


    public static String getWeekText(Day day) {
        String text = "";
        switch (day) {
            case MONDAY:
                text = "星期一";
                break;
            case TUESDAY:
                text = "星期二";
                break;
            case WEDNESDAY:
                text = "星期三";
                break;
            case THURSDAY:
                text = "星期四";
                break;
            case FRIDAY:
                text = "星期五";
                break;
            case SATURDAY:
                text = "星期六";
                break;
            case SUNDAY:
                text = "星期日";
                break;
        }
        return text;
    }


    public static String getModeText(VibrationMode vibrationMode) {
        String modeName = "";
        switch (vibrationMode) {
            case CONTINUOUS_VIBRATION:
                modeName = "持续震动";
                break;
            case INTERMITTENT_VIBRATION1:
                modeName = "间歇震动，震动强度不变";
                break;
            case INTERMITTENT_VIBRATION2:
                modeName = "间歇震动，震动强度由小变大";
                break;
            case INTERMITTENT_VIBRATION3:
                modeName = "间歇震动，震动强度由大变小";
                break;
            case INTERMITTENT_VIBRATION4:
                modeName = "震动强度大小循环";
                break;
        }
        return modeName;
    }

    public static String getScreenText(Page.PageType pageType) {
        String text = "";
                switch (pageType) {
                    case CALORIE:
                        text="卡路里";
                        break;
                    case DISTANCE:
                        text="距离";
                        break;
                    case STEP:
                        text="步数";
                        break;
                    case TIME:
                        text="时间";
                        break;
                    case YOGA:
                        text="瑜伽";
                        break;
                    case TAIJI:
                        text="太极";
                        break;
                    case ALIPAY:
                        text="支付宝";
                        break;
                    case GAMING:
                        text="电竞";
                        break;
                    case BATTERY:
                        text="电量";
                        break;
                    case CYCLING:
                        text="骑行";
                        break;
                    case RUNNING:
                        text="跑步";
                        break;
                    case WALKING:
                        text="健走";
                        break;
                    case WEATHER:
                        text="天气";
                        break;
                    case CLIMBING:
                        text="攀岩";
                        break;
                    case FOOTBALL:
                        text="足球";
                        break;
                    case SWIMMING:
                        text="游泳";
                        break;
                    case BADMINTON:
                        text="羽毛球";
                        break;
                    case HEARTRATE:
                        text="心率";
                        break;
                    case PING_PONG:
                        text="乒乓";
                        break;
                    case STOPWATCH:
                        text="秒表";
                        break;
                    case TREADMILL:
                        text="室内跑";
                        break;
                    case BASKETBALL:
                        text="篮球";
                        break;
                    case DAILY_DATA:
                        text="每日数据";
                        break;
                    case ELLIPTICAL:
                        text="椭圆机";
                        break;
                    case VOLLEYBALL:
                        text="排球";
                        break;
                    case FITNESSDANCE:
                        text="健身舞";
                        break;
                    case BODY_BUILDING:
                        text="力量训练";
                        break;
                    case AEROBIC_WORKOUT:
                        text="有氧运动";
                        break;
                    case AEROBICS_RUN_12MINS:
                        text="12分钟跑";
                        break;
                    case AEROBICS_WALK_6MINS:
                        text="6分钟健走";
                        break;

                }
        return text;
    }

    public static String getMessageTypeText(Call.ReminderType reminderType) {
        String text = "自定义提醒";
        if(reminderType == null) {
            return text;
        }
        switch (reminderType) {
            case SMS:
                text="短信提醒";
                break;
            case CALL:
                text="来电提醒";
                break;
            case QQ:
                text="QQ提醒";
                break;
            case WECHAT:
                text="微信提醒";
                break;
            case LINE:
                text="Line";
                break;
            case GMAIL:
                text="Gmail";
                break;
            case TWITTER:
                text="Twitter";
                break;
            case FACEBOOK:
                text="Facebook";
                break;
            case WHATSAPP:
                text="WhatsApp";
                break;
            case KAKAOTALK:
                text="Kakao Talk";
                break;
            case SEWELLNESS:
                text="Sewellneess";
                break;

        }
        return text;
    }
}
