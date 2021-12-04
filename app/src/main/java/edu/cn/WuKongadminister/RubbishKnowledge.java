package edu.cn.WuKongadminister;

public class RubbishKnowledge {
    private static String 可回收物 = "可回收物主要包括废纸、塑料、玻璃、金属和布料五大类。" +
            "这些垃圾通过综合处理回收利用，可以减少污染，节省资源。" +
            "如每回收1吨废纸可造好纸850公斤，节省木材300公斤，比等量生产减少污染74%；每回收1吨塑料饮料瓶可获得0.7吨二级原料；" +
            "每回收1吨废钢铁可炼好钢0.9吨，比用矿石冶炼节约成本47%，减少空气污染75%，减少97%的水污染和固体废物。\n";
    private static String 厨余垃圾 = "厨余垃圾（上海称湿垃圾）包括剩菜剩饭、骨头、菜根菜叶、果皮等食品类废物。" +
            "经生物技术就地处理堆肥，每吨可生产0.6~0.7吨有机肥料。\n";
    private static String 有害垃圾 = "有害垃圾含有对人体健康有害的重金属、有毒的物质或者对环境造成现实危害或者潜在危害的废弃物。" +
            "包括电池、荧光灯管、灯泡、水银温度计、油漆桶、部分家电、过期药品及其容器、过期化妆品等。这些垃圾一般使用单独回收或填埋处理。\n";
    private static String 其他垃圾 = "其他垃圾（上海称干垃圾）包括除上述几类垃圾之外的砖瓦陶瓷、渣土、卫生间废纸、纸巾等难以回收的废弃物及尘土、食品袋（盒）。" +
            "采取卫生填埋可有效减少对地下水、地表水、土壤及空气的污染。\n";
    private static String mean = "垃圾分类是垃圾终端处理设施运转的基础，实施生活垃圾分类，可以有效改善城乡环境，促进资源回收利用。" +
            "应在生活垃圾科学合理分类的基础上，对应开展生活垃圾分类配套体系建设，根据分类品种建立与垃圾分类相配套的收运体系、" +
            "建立与再生资源利用相协调的回收体系，完善与垃圾分类相衔接的终端处理设施，以确保分类收运、回收、利用和处理设施相互衔接。" +
            "只有做好垃圾分类，垃圾回收及处理等配套系统才能更高效地运转。垃圾分类处理关系到资源节约型、环境友好型社会的建设，" +
            "有利于我国新型城镇化质量和生态文明建设水平的进一步提高。\n";
    private static String advantage = "垃圾分类处理的优点如下：" +
            "一、减少占地。垃圾分类，去掉能回收的，不易降解的物质，减少垃圾数量达50%以上。" +
            "二、减少环境污染。废弃的电池等含有金属汞等有毒物质，会对人类产生严重的威胁，废塑料进入土壤，会导致农作物减产，因此回收利用可以减少这些危害。" +
            "三、变废为宝。1吨废塑料可回炼600公斤无铅汽油和柴油。回收1500吨废纸，可避免砍伐用于生产1200吨纸的林木。因此，垃圾回收既环保，又节约资源。\n";

    public RubbishKnowledge(){}

    public static String get(String type) {
        String result;
        switch (type) {
            case "可回收物":
                result = getRecoverable() + getMean() + getAdvantage();
                break;
            case "厨余垃圾":
                result = getKitchen() + getMean() + getAdvantage();
                break;
            case "有害垃圾":
                result = getHarmful() + getMean() + getAdvantage();
                break;
            case "其他垃圾":
                result = getOther() + getMean() + getAdvantage();
                break;
            default:
                result = mean;
                break;
        }
        return result;
    }

    private static String getRecoverable() {
        return 可回收物;
    }

    private static String getKitchen() {
        return 厨余垃圾;
    }

    private static String getHarmful() {
        return 有害垃圾;
    }

    private static String getOther() {
        return 其他垃圾;
    }

    public static String getAdvantage() {
        return advantage;
    }

    public static String getMean() {
        return mean;
    }
}
