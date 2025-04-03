package WM.util;

public class Constain {
    public interface ClASS {
        String CLASS_CLASSCODE_BLANK = " Ma lop khon de trong";
        String CLASS_CLASSCODE_EXSIST = "Ma lop da ton tai";
        String CLASS_NAME_BLANK = "Ten lop khong de trong";
        String LIST_ERRORS="Danh sach loi";
    }
    public interface TEACHER{
        String TEACHER_NOTFOUND="Giao vien khong ton tai";
        String TEACHER_CODE_BLANK="ma giao vien khong duoc de trong";
        String TEACHER_NAME_BLANK="Ten gia vien khong de trong";
        String TEACHER_CODE_EXSIST="Ma giao vien da ton tai";
        String LIST_ERRORS="Danh sach loi";
    }
    public interface ERRORCODE {
        String INTERNAL_SERVER_ERROR = "500";
        String INTERNAL_SERVER_ERROR_MESSAGE = "INTERNAL_SERVER_ERROR";
        String BAD_REQUEST = "400";
        String BAD_REQUEST_MESSAGE = "Dữ liệu truyền vào không hợp lệ";
        String DATA_NOT_FOUND = "HRM_404";
        String DATA_NOT_FOUND_MESSAGE = "DATA_NOT_FOUND";
        String MONTH_YEAR_MESSAGE = "Định dạng ngày nhập vào không đúng, chỉ nhập tháng và năm";
        String ERROR_JOB_INFO_STATUS = "Trạng thái phải là inactive mới được xóa";
    }
}
