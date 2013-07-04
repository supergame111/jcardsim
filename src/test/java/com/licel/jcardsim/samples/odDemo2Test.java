/*
 * Copyright 2013 Licel LLC.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.licel.jcardsim.samples;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Properties;
import junit.framework.TestCase;
import com.licel.jcardsim.base.SimulatorSystem;
import com.licel.jcardsim.utils.APDUScriptTool;

/**
 * Test javacard sample (odDemo2).
 */
public class odDemo2Test extends TestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of javacard sample (odDemo2).
     */
    public void testExecuteCommands() throws Exception {

        System.out.println("executeCommands");

        Properties cfg = new Properties();
        cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.AID", "a00000006203010c070101");
        cfg.setProperty("com.licel.jcardsim.smartcardio.applet.0.Class", "com.licel.jcardsim.samples.odSample.packageA.A");
        cfg.setProperty("com.licel.jcardsim.smartcardio.applet.1.AID", "a00000006203010c070201");
        cfg.setProperty("com.licel.jcardsim.smartcardio.applet.1.Class", "com.licel.jcardsim.samples.odSample.packageB.B");

        StringBuilder sb = new StringBuilder();
        sb.append("powerup;\n");
        sb.append("//Scenario - package deletion memory check\n");
        sb.append("// create Applet A's first instance\n");
        sb.append("0x80 0xB8 0x00 0x00 0x0D 0x0B 0xA0 0x00 0x00 0x00 0x62 0x03 0x01 0x0C 0x07 0x01 0x01 0x00 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("// Select Applet A's first instance\n");
        sb.append("0x00 0xA4 0x04 0x00 0x0B 0xA0 0x00 0x00 0x00 0x62 0x03 0x01 0x0C 0x07 0x01 0x01 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("//request Object Deletion\n");
        sb.append("0xC0 0x10 0x00 0x00 0x00 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("//capture initial memory\n");
        sb.append("0xC0 0x17 0x00 0x00 0x00 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("//create Applet B's instance\n");
        sb.append("0x80 0xB8 0x00 0x00 0x0D 0x0B 0xA0 0x00 0x00 0x00 0x62 0x03 0x01 0x0C 0x07 0x02 0x01 0x00 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("//Delete Applet B's instance\n");
        sb.append("0x80 0xc4 0x01 0x00 0x0C 0x0B 0xA0 0x00 0x00 0x00 0x62 0x03 0x01 0x0C 0x07 0x02 0x01 0x7F;\n");
        sb.append("//90 00\n");
        sb.append("//select Applet A's instance\n");
        sb.append("0x00 0xA4 0x04 0x00 0x0B 0xA0 0x00 0x00 0x00 0x62 0x03 0x01 0x0C 0x07 0x01 0x01 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("//verify memory returned\n");
        sb.append("0xC0 0x18 0x00 0x00 0x00 0x7F;\n");
        sb.append("// 90 00 \n");
        sb.append("powerdown;\n");

        InputStream commandsStream = new ByteArrayInputStream(sb.toString().replaceAll("\n", System.getProperty("line.separator")).getBytes());     
        boolean isException = true;
        try {
            SimulatorSystem.resetRuntime();
            APDUScriptTool.executeCommands(cfg, commandsStream, null);
            isException = false;
        } catch (Throwable t) {
            t.printStackTrace();
        }
        assertEquals(isException, false);
        commandsStream.close();
    }
}