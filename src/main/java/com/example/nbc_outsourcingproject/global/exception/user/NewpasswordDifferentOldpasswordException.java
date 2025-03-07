package com.example.nbc_outsourcingproject.global.exception.user;

import com.example.nbc_outsourcingproject.global.exception.OutsourcingException;
import com.example.nbc_outsourcingproject.global.exception.errorcode.UserErrorCode;

public class NewpasswordDifferentOldpasswordException extends OutsourcingException {
  public NewpasswordDifferentOldpasswordException() {
    super(UserErrorCode.NEWPASSWORD_DIFFERENT_OLDPASSWORD);
  }
}
