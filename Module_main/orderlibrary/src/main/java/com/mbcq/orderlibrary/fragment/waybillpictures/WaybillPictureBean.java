package com.mbcq.orderlibrary.fragment.waybillpictures;

public class WaybillPictureBean {

    /**
     * billno : 10030004823
     * imgtype : 16
     * imgtypestr : 异常登记
     * imgsize : 2.3
     * uploaddate : 2020-12-08T11:02:40
     * uploadman :
     * imgpath : /Content/Images/images/allimages/badwaybill/20201207_205854.jpg
     * imgname : 20201207_205854.jpg
     * imageid : 1
     */

    private String billno;
    private int imgtype;
    private String imgtypestr;
    private double imgsize;
    private String uploaddate;
    private String uploadman;
    private String imgpath;
    private String imgname;
    private String imageid;

    public String getBillno() {
        return billno;
    }

    public void setBillno(String billno) {
        this.billno = billno;
    }

    public int getImgtype() {
        return imgtype;
    }

    public void setImgtype(int imgtype) {
        this.imgtype = imgtype;
    }

    public String getImgtypestr() {
        return imgtypestr;
    }

    public void setImgtypestr(String imgtypestr) {
        this.imgtypestr = imgtypestr;
    }

    public double getImgsize() {
        return imgsize;
    }

    public void setImgsize(double imgsize) {
        this.imgsize = imgsize;
    }

    public String getUploaddate() {
        return uploaddate;
    }

    public void setUploaddate(String uploaddate) {
        this.uploaddate = uploaddate;
    }

    public String getUploadman() {
        return uploadman;
    }

    public void setUploadman(String uploadman) {
        this.uploadman = uploadman;
    }

    public String getImgpath() {
        return imgpath;
    }

    public void setImgpath(String imgpath) {
        this.imgpath = imgpath;
    }

    public String getImgname() {
        return imgname;
    }

    public void setImgname(String imgname) {
        this.imgname = imgname;
    }

    public String getImageid() {
        return imageid;
    }

    public void setImageid(String imageid) {
        this.imageid = imageid;
    }
}
