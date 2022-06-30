package com.khoders.pms.jbeans;

/**
 *
 * @author richard
 */
public class ReportFiles
{
  private static final String BASE_DIR = "/com/khoders/pms/resources/reports/";
  private static final String LOGO_DIR = "/com/khoders/pms/resources/images/";
  
  public static final String LOGO = LOGO_DIR+"logo.png";
  public static final String RECEIPT_FILE = BASE_DIR+"receipt.jasper";
  public static final String CASH_RECEIPT_FILE = BASE_DIR+"cash_receipt.jasper";
  public static final String PROFORMA_INVOICE = BASE_DIR+"proforma_invoice.jasper";
  public static final String INVOICE = BASE_DIR+"invoice.jasper";
}