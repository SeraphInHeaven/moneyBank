package com.example.demo;

import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.IntsRefBuilder;
import org.apache.lucene.util.fst.Builder;
import org.apache.lucene.util.fst.FST;
import org.apache.lucene.util.fst.PositiveIntOutputs;
import org.apache.lucene.util.fst.Util;
import org.junit.Test;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2020-03-19 2:28 PM
 */
public class FSTTest {

    @Test
    public void testBuild() throws IOException {


//        String inputValues[] = {"cat", "cats", "december", "dog", "dogs", "november"};
//        long outputValues[] = {666, 999, 7, 999, 666, 12};

        // Input values (keys). These must be provided to Builder in Unicode sorted order!
        String inputValues[] = {"dog", "dogs"};
        long outputValues[] = {999, 666};

        PositiveIntOutputs outputs = PositiveIntOutputs.getSingleton();
        Builder<Long> builder = new Builder<Long>(FST.INPUT_TYPE.BYTE1, outputs);
        //BytesRef scratchBytes = new BytesRef();
        IntsRefBuilder scratchInts = new IntsRefBuilder();
        for (int i = 0; i < inputValues.length; i++) {
            BytesRef scratchBytes = new BytesRef(inputValues[i]);
            builder.add(Util.toIntsRef(scratchBytes, scratchInts), outputValues[i]);
        }
        FST<Long> fst = builder.finish();
        File file = new File("/Users/Seraph/Downloads/a.dot");
        Util.toDot(fst, new FileWriter(file), true, true);
    }
}
