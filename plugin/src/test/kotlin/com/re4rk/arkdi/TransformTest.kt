/*
 * Copyright (C) 2020 The Dagger Authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import org.gradle.testkit.runner.TaskOutcome
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder

class TransformTest {

    @get:Rule
    val testProjectDir = TemporaryFolder()

    private lateinit var gradleRunner: GradleTestRunner

    @Before
    fun setup() {
        gradleRunner = GradleTestRunner(testProjectDir)
        gradleRunner.addSrc(
            srcPath = "minimal/MainActivity.java",
            srcContent =
            """
        package minimal;

        import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;

        public class MainActivity extends AppCompatActivity {
          @Override
          public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
          }
        }
            """.trimIndent(),
        )
    }

    // Simple functional test to verify transformation.
    @Test
    fun testAssemble() {
        gradleRunner.addDependencies(
            "implementation 'androidx.appcompat:appcompat:1.1.0'",
            // "implementation 'com.google.dagger:hilt-android:LOCAL-SNAPSHOT'",
            // "annotationProcessor 'com.google.dagger:hilt-compiler:LOCAL-SNAPSHOT'",
        )
        gradleRunner.addActivities(
            "<activity android:name=\".MainActivity\"/>",
        )

        val result = gradleRunner.build()
        val assembleTask = result.getTask(":assembleDebug")
        Assert.assertEquals(TaskOutcome.SUCCESS, assembleTask.outcome)

        // val transformedClass = result.getTransformedFile("minimal/MainActivity.class")
        // FileInputStream(transformedClass).use { fileInput ->
        //     ClassFile(DataInputStream(fileInput)).let { classFile ->
        //         // Verify superclass is updated
        //         Assert.assertEquals("minimal.Hilt_MainActivity", classFile.superclass)
        //         // Verify super call is also updated
        //         val constPool = classFile.constPool
        //         classFile.methods.first { it.name == "onCreate" }.let { methodInfo ->
        //             // bytecode of MainActivity.onCreate() is:
        //             // 0 - aload_0
        //             // 1 - aload_1
        //             // 2 - invokespecial
        //             // 5 - return
        //             val invokeIndex = 2
        //             val methodRef = ByteArray.readU16bit(methodInfo.codeAttribute.code, invokeIndex + 1)
        //             val classRef = constPool.getMethodrefClassName(methodRef)
        //             Assert.assertEquals("minimal.Hilt_MainActivity", classRef)
        //         }
        //     }
        // }
    }
}
