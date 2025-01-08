package com.jonas.myp_sb.web.myp502.enums;

public enum effectEnum {
    //A 觸發時機
    //B 發動條件
    //C 標籤分類
    //D 影響對方
    //E 增益我方

    A1("A1","登場時"," and (oci.effect like '%【登場時】%' and oci.effect not like '%【登場時】効果%') "),
    A2("A2","攻擊時"," and oci.effect like '%【アタック時】%' "),
    A3("A3","起動效果(角色/領導)"," and oci.effect like '%【起動メイン】%' "),
    A4("A4","KO時"," and oci.effect REGEXP 'このキャラ.*KOされた時|【KO時】' "),
    A6("A6","我方回合中"," and oci.effect like '%【自分のターン中】%' "),
    A7("A7","對方回合中"," and oci.effect like '%【相手のターン中】%' "),
    A8("A8","我方回合結束時"," and oci.effect like '%【自分のターン終了時】%' "),
    A9("A9","對手攻擊時"," and oci.effect like '%【相手のアタック時】%' "),
    A10("A10","打擊對方生命卡時"," and oci.effect like '%ライフにダメージ%' "),
    A11("A11","從手牌登場時"," and oci.effect REGEXP '手札から登場させた時' "),
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
    B15("B15","手牌回牌組底下(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP 'デッキの下に置' " +
            " and SUBSTRING_INDEX(oci.effect, '：', 1) REGEXP '手札' "),
    B16("B16","生命卡轉背面(代價)"," and oci.effect like '%：%' and SUBSTRING_INDEX(oci.effect, '：', 1)  REGEXP '裏向きに' "),
    B17("B17","綁領導"," and oci.effect REGEXP '自分のリーダー.*(場合|を持)' "),
    C1("C1","阻擋"," and (oci.effect like '%【ブロッカー】%' and oci.effect not like '%【ブロッカー】を発動%') "),
    C2("C2","速攻"," and oci.effect like '%【速攻】%' "),
    C3("C3","雙重攻擊"," and oci.effect like '%【ダブルアタック】%' "),
    C4("C4","消失"," and oci.effect like '%【バニッシュ】%' "),
    C5("C5","規則上"," and oci.effect like '%ルール上%' "),
    C6("C5","事件反擊"," and oci.effect like '%【カウンター】%' "),
    D1("D1","對方丟手牌"," and oci.effect REGEXP '相手.*枚を捨て' "),
    D2("D2","減力量"," and oci.effect REGEXP '相手.*パワー-' "),
    D3("D3","KO"," and oci.effect REGEXP '相手.*(KOする|KOし)' "),
    D4("D4","減費用"," and oci.effect like '%コスト-%' "),
    D5("D5","對手無法轉正"," and oci.effect REGEXP '自身の場のドン.*デッキに戻' "),
    D6("D6","對手減咚"," and oci.effect REGEXP '相手の.*アクティブにならない' "),
    D7("D7","減對手生命卡"," and oci.effect REGEXP '相手のライフ.*(トラッシュに|持ち主のデッキの下に置)'and oci.effect not like '%ライフにダメージ%' "),
    D8("D8","橫置對手"," and oci.effect REGEXP '(?!.*【ブロッカー】)相手.*(枚まで|すべて).*(レストにする|レストにし)' "),
    D9("D9","從墓地加入手牌"," and oci.effect REGEXP 'トラッシュ.*手札に加え' "),
    D10("D10","不能發動阻擋"," and oci.effect like '%【ブロッカー】を発動%' "),
    D11("D11","跳怪"," and oci.effect REGEXP '(キャラカード.*枚までを|手札.*枚までを).*登場させ' "),
    E1("E1","抽牌","  and oci.effect REGEXP '枚.*を引' "),
    E2("E2","加力量"," and oci.effect like '%パワー+%' "),
    E3("E3","不會被KO"," and oci.effect REGEXP 'KOされない' "),
    E4("E4","加費用"," and oci.effect like '%コスト+%' "),
    E5("E5","轉正"," and oci.effect REGEXP 'アクティブに(し|する)' "),
    E6("E6","加咚"," and oci.effect REGEXP 'ドン.*追加' "),
    E7("E7","加生命卡"," and oci.effect REGEXP '自分の.*ライフの上に加える' "),
    E8("E8","從牌組堆墓"," and oci.effect REGEXP '自分のデッキの上から.*枚をトラッシュに' "),
    E9("E9","翻牌找牌"," and oci.effect REGEXP 'デッキの上から.*手札に加え' "),
    E10("E10","角色回手"," and oci.effect REGEXP '枚まで.*持ち主の手札に戻' "),
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
