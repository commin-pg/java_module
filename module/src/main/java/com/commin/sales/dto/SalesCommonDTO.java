package com.commin.sales.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SalesCommonDTO {

    public SalesCommonDTO(String retailerName, String serviceType) {
        this.retailerName = retailerName;
        this.serviceType = serviceType;
    }

    private String retailerName;
    private String serviceType;
    private String productType;

    private String salesBegin;
    private String salesEnd;

    private String barcode;
    private String isrcCode;
    private String artistName;
    private String albumName;
    private String trackName;
    private String labelName;
    private String salesYearMonth;
    private String salesCountry;
    private String quantity;
    private String unitPrice;
    private String appliedTaxRate;
    private String appliedExchangeRate;
    private String appliedCurrency;

    private String salesType;

    public void setVariable(String dtoColumnName, String data) throws Exception {
        switch (dtoColumnName.toUpperCase()) {
            case "BARCODE":
                setBarcode(data);
                break;
            case "ISRCCODE":
                setIsrcCode(data);
                break;
            case "ALBUMNAME":
                setAlbumName(data);
            case "ARTISTNAME":
                setArtistName(data);
                break;
            case "TRACKNAME":
                setTrackName(data);
                break;
            case "LABELNAME":
                setLabelName(data);
                break;
            case "SALESYEARMONTH":
                setSalesYearMonth(data);
                break;
            case "QUANTITY":
                setQuantity(data);
                break;
            case "APPLIEDTAXRATE":
                setAppliedTaxRate(data);
                break;
            case "APPLIEDEXCHANGERATE":
                setAppliedExchangeRate(data);
                break;
            case "APPLIEDCURRENCY":
                setAppliedCurrency(data);
                break;
            case "SALESCOUNTRY":
                setSalesCountry(data);
                break;
            default:
                throw new Exception("No Mapping Column : " + dtoColumnName);
        }

    }

    // // 판매월
    // private String salesDate;
    // // Channel
    // private String channel;

    // // 서비스상품
    // private String serviceGoods;
    // // Quantity
    // private String quantity;
    // // Wholesale Price
    // private String price;
    // // Partner Share/Q x Wsp
    // private String shareQxWsp;

    // // Post-TAX Subtotal
    // private String postTaxSubTotal;
    // // Currency
    // private String currency;
    // // 기획사코드
    // private String labelCode;
    // // 기획사명
    // private String labelName;
    // // 계약형태
    // private String contractType;
    // // 곡코드
    // private String trackCode;
    // // 앨범코드
    // private String albumCode;
    // // UPC
    // private String upc;
    // // ISRC
    // private String isrc;
    // // Album
    // private String albumName;
    // // Artist
    // private String artist;
    // // Title
    // private String title;
    // // ArtistShare/ LcoalCurrency
    // private String aritistShareLocalCurrency;
    // // ArtistShare/ KRW
    // private String aritstSharekrw;
}
