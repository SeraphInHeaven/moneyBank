package com.example.demo;

import org.rocksdb.Options;
import org.rocksdb.RocksDB;
import org.rocksdb.RocksDBException;

/**
 * ${DESCRIPTION}
 *
 * @author Seraph
 *         2019-06-29 下午9:58
 */
public class RocksDBTest {



    public static void main(String[] args) {

        System.out.println(Long.toString(Integer.MAX_VALUE, Character.MAX_RADIX));
        System.out.println(Long.toString(Long.MAX_VALUE, Character.MAX_RADIX));

        // a static method that loads the RocksDB C++ library.
        RocksDB.loadLibrary();

        // the Options class contains a set of configurable DB options
        // that determines the behaviour of the database.
        try (final Options options = new Options().setCreateIfMissing(true)) {

            // a factory method that returns a RocksDB instance
            try (final RocksDB db = RocksDB.open(options, "path/to/db")) {
                // do something


            }
        } catch (RocksDBException e) {
            // do some error handling
        }
    }
}
