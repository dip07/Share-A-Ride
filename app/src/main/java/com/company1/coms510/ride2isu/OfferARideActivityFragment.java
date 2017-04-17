package com.company1.coms510.ride2isu;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by dipanjan karmakar
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class OfferARideActivityFragment extends Fragment {

    public OfferARideActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_offer_aride, container, false);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class EditProfileFragment extends Fragment {

        public EditProfileFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            return inflater.inflate(R.layout.fragment_edit_profile2, container, false);
        }
    }
}
