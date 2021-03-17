package com.ust.charvel.utils

import org.junit.Before
import org.mockito.MockitoAnnotations

abstract class MockitoTest {

    @Before
    open fun setup() {
        MockitoAnnotations.initMocks(this)
    }
}