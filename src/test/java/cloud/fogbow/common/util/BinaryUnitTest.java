package cloud.fogbow.common.util;

import org.junit.Assert;
import org.junit.Test;

public class BinaryUnitTest {
    @Test
    public void testByteToKilobyte() {
        // set up
        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        int ONE_KILOBYTE = 1024;
        double value1 = 0.5 * ONE_KILOBYTE;
        double value2 = 1 * ONE_KILOBYTE;
        double value3 = 1.5 * ONE_KILOBYTE;
        double value4 = 2 * ONE_KILOBYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.bytes(value1).asKilobytes();
        double result2 = BinaryUnit.bytes(value2).asKilobytes();
        double result3 = BinaryUnit.bytes(value3).asKilobytes();
        double result4 = BinaryUnit.bytes(value4).asKilobytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testByteToMegabyte() {
        // set up
        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        double ONE_MEGABYTE = Math.pow(1024, 2);
        double value1 = 0.5 * ONE_MEGABYTE;
        double value2 = 1 * ONE_MEGABYTE;
        double value3 = 1.5 * ONE_MEGABYTE;
        double value4 = 2 * ONE_MEGABYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.bytes(value1).asMegabytes();
        double result2 = BinaryUnit.bytes(value2).asMegabytes();
        double result3 = BinaryUnit.bytes(value3).asMegabytes();
        double result4 = BinaryUnit.bytes(value4).asMegabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testByteToGigabyte() {
        // set up
        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        double ONE_GIGABYTE = Math.pow(1024, 3);
        double value1 = 0.5 * ONE_GIGABYTE;
        double value2 = 1 * ONE_GIGABYTE;
        double value3 = 1.5 * ONE_GIGABYTE;
        double value4 = 2 * ONE_GIGABYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.bytes(value1).asGigabytes();
        double result2 = BinaryUnit.bytes(value2).asGigabytes();
        double result3 = BinaryUnit.bytes(value3).asGigabytes();
        double result4 = BinaryUnit.bytes(value4).asGigabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testKilobyteToByte() {
        // set up
        double expected1 = 512;
        double expected2 = 1024;
        double expected3 = 1024 + 512;
        double expected4 = 2048;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.kilobytes(value1).asBytes();
        double result2 = BinaryUnit.kilobytes(value2).asBytes();
        double result3 = BinaryUnit.kilobytes(value3).asBytes();
        double result4 = BinaryUnit.kilobytes(value4).asBytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testKilobyteToMegabyte() {
        // set up
        double ONE_MEGABYTE = Math.pow(1024, 1);

        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        double value1 = 0.5 * ONE_MEGABYTE;
        double value2 = 1 * ONE_MEGABYTE;
        double value3 = 1.5 * ONE_MEGABYTE;
        double value4 = 2 * ONE_MEGABYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.kilobytes(value1).asMegabytes();
        double result2 = BinaryUnit.kilobytes(value2).asMegabytes();
        double result3 = BinaryUnit.kilobytes(value3).asMegabytes();
        double result4 = BinaryUnit.kilobytes(value4).asMegabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testKilobyteToGigabyte() {
        // set up
        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        double ONE_GIGABYTE = Math.pow(1024, 2);
        double value1 = 0.5 * ONE_GIGABYTE;
        double value2 = 1 * ONE_GIGABYTE;
        double value3 = 1.5 * ONE_GIGABYTE;
        double value4 = 2 * ONE_GIGABYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.kilobytes(value1).asGigabytes();
        double result2 = BinaryUnit.kilobytes(value2).asGigabytes();
        double result3 = BinaryUnit.kilobytes(value3).asGigabytes();
        double result4 = BinaryUnit.kilobytes(value4).asGigabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testMegabyteToByte() {
        // set up
        double ONE_MEGABYTE = Math.pow(1024, 2);

        double expected1 = 0.5 * ONE_MEGABYTE;
        double expected2 = ONE_MEGABYTE;
        double expected3 = 1.5 * ONE_MEGABYTE;
        double expected4 = 2 * ONE_MEGABYTE;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.megabytes(value1).asBytes();
        double result2 = BinaryUnit.megabytes(value2).asBytes();
        double result3 = BinaryUnit.megabytes(value3).asBytes();
        double result4 = BinaryUnit.megabytes(value4).asBytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testMegabyteToKilobyte() {
        // set up
        double ONE_MEGABYTE = Math.pow(1024, 1);

        double expected1 = 0.5 * ONE_MEGABYTE;
        double expected2 = 1 * ONE_MEGABYTE;
        double expected3 = 1.5 * ONE_MEGABYTE;
        double expected4 = 2 * ONE_MEGABYTE;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.megabytes(value1).asKilobytes();
        double result2 = BinaryUnit.megabytes(value2).asKilobytes();
        double result3 = BinaryUnit.megabytes(value3).asKilobytes();
        double result4 = BinaryUnit.megabytes(value4).asKilobytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testMegabyteToGigabyte() {
        // set up
        double ONE_MEGABYTE = Math.pow(1024, 1);

        double expected1 = 0.5;
        double expected2 = 1;
        double expected3 = 1.5;
        double expected4 = 2;

        double value1 = 0.5 * ONE_MEGABYTE;
        double value2 = 1 * ONE_MEGABYTE;
        double value3 = 1.5 * ONE_MEGABYTE;
        double value4 = 2 * ONE_MEGABYTE;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.megabytes(value1).asGigabytes();
        double result2 = BinaryUnit.megabytes(value2).asGigabytes();
        double result3 = BinaryUnit.megabytes(value3).asGigabytes();
        double result4 = BinaryUnit.megabytes(value4).asGigabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testGigabyteToByte() {
        // set up
        double ONE_GIGABYTE = Math.pow(1024, 3);

        double expected1 = 0.5 * ONE_GIGABYTE;
        double expected2 = ONE_GIGABYTE;
        double expected3 = 1.5 * ONE_GIGABYTE;
        double expected4 = 2 * ONE_GIGABYTE;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.gigabytes(value1).asBytes();
        double result2 = BinaryUnit.gigabytes(value2).asBytes();
        double result3 = BinaryUnit.gigabytes(value3).asBytes();
        double result4 = BinaryUnit.gigabytes(value4).asBytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testGigabyteToKilobyte() {
        // set up
        double ONE_GIGABYTE = Math.pow(1024, 2);

        double expected1 = 0.5 * ONE_GIGABYTE;
        double expected2 = ONE_GIGABYTE;
        double expected3 = 1.5 * ONE_GIGABYTE;
        double expected4 = 2 * ONE_GIGABYTE;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.gigabytes(value1).asKilobytes();
        double result2 = BinaryUnit.gigabytes(value2).asKilobytes();
        double result3 = BinaryUnit.gigabytes(value3).asKilobytes();
        double result4 = BinaryUnit.gigabytes(value4).asKilobytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }

    @Test
    public void testGigabyteToMegabyte() {
        // set up
        double ONE_GIGABYTE = Math.pow(1024, 1);

        double expected1 = 0.5 * ONE_GIGABYTE;
        double expected2 = ONE_GIGABYTE;
        double expected3 = 1.5 * ONE_GIGABYTE;
        double expected4 = 2 * ONE_GIGABYTE;

        double value1 = 0.5;
        double value2 = 1;
        double value3 = 1.5;
        double value4 = 2;

        double delta = 0;

        // exercise
        double result1 = BinaryUnit.gigabytes(value1).asMegabytes();
        double result2 = BinaryUnit.gigabytes(value2).asMegabytes();
        double result3 = BinaryUnit.gigabytes(value3).asMegabytes();
        double result4 = BinaryUnit.gigabytes(value4).asMegabytes();

        // verify
        Assert.assertEquals(expected1, result1, delta);
        Assert.assertEquals(expected2, result2, delta);
        Assert.assertEquals(expected3, result3, delta);
        Assert.assertEquals(expected4, result4, delta);
    }
}
