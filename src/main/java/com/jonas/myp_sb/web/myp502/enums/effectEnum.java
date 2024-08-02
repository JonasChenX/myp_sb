package com.jonas.myp_sb.web.myp502.enums;

public enum effectEnum {
    //A 觸發時機
    //B 發動條件
    //C 標籤分類
    //D 效果細項分類

    A1("A1","登場時"," and (oci.effect like '%【登場時】%' and oci.effect not like '%【登場時】効果%') "),
    A2("A2","攻擊時"," and oci.effect like '%【アタック時】%' "),
    A3("A3","起動效果(角色/領導)"," and oci.effect like '%【起動メイン】%' "),
    A4("A4","KO時"," and oci.effect REGEXP 'このキャラ.*KOされた時|【KO時】' "),
    A5("A5","登場時"," and oci.effect like '%【登場時】%' "),
    A6("A6","我方回合中"," and oci.effect like '%【自分のターン中】%' "),
    A7("A7","對方回合中"," and oci.effect like '%【相手のターン中】%' "),
    A8("A8","我方回合結束時"," and oci.effect like '%【自分のターン終了時】%' "),
    A9("A9","對手攻擊時"," and oci.effect like '%【相手のアタック時】%' "),
    B1("B1","貼咚"," and (oci.effect like '%!!×%' or oci.effect like '%‼×%') "),
    B2("B2","回咚"," and (oci.effect like '%‼-%' or oci.effect like '%!!-%') "),
    B3("B3","橫置咚"," and oci.effect REGEXP '①|コストエリアのドン' "),
    B4("B4","一回合一次"," and oci.effect like '%ターン1回%' "),
    B5("B5","生命卡加手(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'ライフ.*手札に加え' "),
    B6("B6","生命卡轉正面(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'ライフ.*を表向きにでき' "),
    B7("B7","生命卡送墓地(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'ライフ.*枚をトラッシュ' "),
    B8("B8","丟手牌(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP '手札.*を捨て' "),
    B9("B9","回手牌(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'を持ち主の手札に戻' "),
    B10("B10","角色/領導/場地橫置(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'リーダー|キャラ|ステージ.*レストに' "),
    B11("B11","減力量(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'パワー-.*する' "),
    B12("B12","該角色送墓地(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'このキャラをトラッシュに' "),
    B13("B13","角色/場地回牌組底下(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'キャラ|ステージ.*デッキの下に置' "),
    B14("B14","墓地回牌組底下(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'デッキの下に置' " +
            " and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'トラッシュ' "),
    B15("B15","墓地回牌組底下(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'デッキの下に置' " +
            " and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP '手札' "),
    B16("B16","生命卡轉背面(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1)  REGEXP '裏向きに' "),
    C1("C1","阻擋"," and (oci.effect like '%【ブロッカー】%' and oci.effect not like '%【ブロッカー】を発動%') "),
    C2("C2","速攻"," and oci.effect like '%【速攻】%' "),
    C3("C3","雙重攻擊"," and oci.effect like '%【ダブルアタック】%' "),
    C4("C4","消失"," and oci.effect like '%【バニッシュ】%' "),
    C5("C5","規則上"," and oci.effect like '%ルール上%' "),
    D1("D1","抽牌"," and oci.effect like '%枚を引く%' "),
    D2("D2","不能發動阻擋"," and oci.effect like '%【ブロッカー】を発動%' ")
    ;
    private String effectCode;
    private String text;
    private String sql;

    effectEnum(String effectCode, String text, String sql) {
        this.effectCode = effectCode;
        this.text = text;
        this.sql = sql;
    }

    public String getEffectCode() {
        return effectCode;
    }

    public String getText() {
        return text;
    }

    public String getSql() {
        return sql;
    }

    public static String codeToSql(String code){
        for (effectEnum effects : effectEnum.values()) {
            if (code.equals(effects.getEffectCode())) {
                return effects.getSql();
            }
        }
        throw new IllegalArgumentException("Invalid status code: " + code);
    }
}
