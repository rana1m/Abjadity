package com.rana.abjadity;

import org.junit.Test;


import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ParentHomePageActivityTest {

    @Test(expected = Exception.class)
    public void addChildToParent_check() {

        ParentHomePageActivity parentHomePageActivity=mock(ParentHomePageActivity.class);
        doNothing().when(parentHomePageActivity).addChildToParent();
        parentHomePageActivity.addChildToParent();

        verify(parentHomePageActivity, times(1)).addChildToParent();



    }


}