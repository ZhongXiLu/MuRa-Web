package com.github.muraweb;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UtilTest {

    @Test
    public void getRepoNameTest() {
        assertEquals("OpenFeign/feign", Util.getRepoName("https://github.com/OpenFeign/feign"));
        assertEquals("ZhongXiLu/LuMutator", Util.getRepoName("https://github.com/ZhongXiLu/LuMutator"));
        assertEquals("ZhongXiLu/MuRa-Web", Util.getRepoName("https://github.com/ZhongXiLu/MuRa-Web"));

        assertEquals("ZhongXiLu/MuRa-Web", Util.getRepoName("https://github.com/ZhongXiLu/MuRa-Web/"));
        assertEquals("ZhongXiLu/MuRa-Web", Util.getRepoName("https://github.com/ZhongXiLu/MuRa-Web///"));
    }

}