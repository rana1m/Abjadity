package com.rana.abjadity;

import org.junit.Test;

import static org.mockito.Mockito.doCallRealMethod;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.internal.verification.VerificationModeFactory.times;

public class NumStepFourActivityTest {

    @Test(expected = Exception.class)
    public void NextStepTest() {

        NumStepFourActivity numStepFourActivity = mock(NumStepFourActivity.class);
        doCallRealMethod().when(numStepFourActivity).check_Next_Step();
        numStepFourActivity.check_Next_Step();
        verify(numStepFourActivity, times(1)).check_Next_Step();

    }
}