package com.commin.sales.models;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesCommonDTO {
    // 정산월
    private String calculateDate;
    // 판매월
    private String salesDate;
    // Channel
    private String channel;

    // 서비스상품
    private String serviceGoods;
    // Quantity
    private String quantity;
    // Wholesale Price
    private String price;
    // Partner Share/Q x Wsp
    private String shareQxWsp;

    // Post-TAX Subtotal
    private String postTaxSubTotal;
    // Currency
    private String currency;
    // 기획사코드
    private String labelCode;
    // 기획사명
    private String labelName;
    // 계약형태
    private String contractType;
    // 곡코드
    private String trackCode;
    // 앨범코드
    private String albumCode;
    // UPC
    private String upc;
    // ISRC
    private String isrc;
    // Album
    private String albumName;
    // Artist
    private String artist;
    // Title
    private String title;
    // ArtistShare/ LcoalCurrency
    private String aritistShareLocalCurrency;
    // ArtistShare/ KRW
    private String aritstSharekrw;
}
