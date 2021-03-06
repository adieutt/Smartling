/*
 * Copyright 2012 Smartling, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this work except in compliance with the License.
 * You may obtain a copy of the License in the LICENSE file, or at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.smartling.api.sdk.file.commandline;

import static junit.framework.Assert.assertNotNull;

import com.smartling.api.sdk.exceptions.ApiException;
import java.io.File;

import com.smartling.api.sdk.ApiTestHelper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;

public class RetrieveFileITest
{
    @Test
    public void testRetrieveFile() throws ApiException, IOException
    {
        List<String> argList = buildFileRetrieveArgs();
        String[] args = new String[] {};
        File file = RetrieveFile.retrieve(argList.toArray(args));
        assertNotNull(file);
    }

    @Test(expected = Exception.class)
    public void testInvalidNumberOfArguments() throws ApiException, IOException
    {
        RetrieveFile.retrieve(new String[]{});
    }

    private List<String> buildFileRetrieveArgs()
    {
        List<String> args = new ArrayList<String>();
        args.add(String.valueOf(ApiTestHelper.getTestMode()));
        args.add(ApiTestHelper.getApiKey());
        args.add(ApiTestHelper.getProjectId());
        args.add(ApiTestHelper.getTestFile().getAbsolutePath());
        args.add(ApiTestHelper.getLocale());
        args.add("bin/");

        return args;
    }
}
