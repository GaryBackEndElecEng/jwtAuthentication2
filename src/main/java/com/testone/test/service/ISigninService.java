package com.testone.test.service;

import com.testone.test.model.Signin;
import com.testone.test.model.UserToken;
import com.testone.test.model.User_;

public interface ISigninService {
    public Boolean verfiyPassword(Signin signin);

    User_ getUser(Signin signin);


    UserToken addToken(User_ user);
}
