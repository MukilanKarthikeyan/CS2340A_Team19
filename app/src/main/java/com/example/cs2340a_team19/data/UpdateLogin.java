package com.example.cs2340a_team19.data;

import com.example.cs2340a_team19.data.model.LoggedInUser;

public interface UpdateLogin {
    void primary(Result<LoggedInUser> result);
}
