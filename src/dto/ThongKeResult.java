package dto;

public class ThongKeResult {
    private int soLuongVe;
    private double tongTien;

    public ThongKeResult(int soLuongVe, double tongTien) {
        this.soLuongVe = soLuongVe;
        this.tongTien = tongTien;
    }

    public int getSoLuongVe() {
        return soLuongVe;
    }

    public double getTongTien() {
        return tongTien;
    }
}
