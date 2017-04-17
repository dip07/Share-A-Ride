package com.company1.coms510.ride2isu.DataAccessUsingFirebase;

/*
 * Created by Viswanath on 10/12/2016.
 */

public interface DataAccessLayer {

    public void setData(String condition, Object object);
    public void fetchData(String condition, FirebaseHelper firebaseHelper);

}

