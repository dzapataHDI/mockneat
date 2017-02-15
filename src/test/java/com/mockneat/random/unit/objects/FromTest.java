package com.mockneat.random.unit.objects;

import com.mockneat.random.RandTestConstants;
import com.mockneat.random.interfaces.RandUnitDouble;
import com.mockneat.random.interfaces.RandUnitInt;
import com.mockneat.random.interfaces.RandUnitLong;
import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import java.util.*;

import static com.mockneat.random.RandTestConstants.RAND;
import static com.mockneat.random.RandTestConstants.RANDS;
import static com.mockneat.random.RandTestConstants.RU_CYCLES;
import static java.util.Arrays.asList;
import static java.util.Arrays.stream;
import static com.mockneat.random.utils.FunctUtils.cycle;
import static org.apache.commons.lang3.ArrayUtils.toObject;
import static org.junit.Assert.assertTrue;

public class FromTest {

    private static class TestModel {
        private String x1;
        private Integer y1;

        public TestModel(String x1, Integer y1) {
            this.x1 = x1;
            this.y1 = y1;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            TestModel testModel = (TestModel) o;

            if (x1 != null ? !x1.equals(testModel.x1) : testModel.x1 != null) return false;
            return y1 != null ? y1.equals(testModel.y1) : testModel.y1 == null;

        }

        @Override
        public int hashCode() {
            int result = x1 != null ? x1.hashCode() : 0;
            result = 31 * result + (y1 != null ? y1.hashCode() : 0);
            return result;
        }

        public static TestModel[] getTestArray() {
            return new TestModel[]{
                    new TestModel("a", 1),
                    new TestModel("b", 2),
                    new TestModel("c", 3),
                    new TestModel("d", 4),
                    new TestModel("xa", 100),
                    new TestModel("ha", 200),
                    new TestModel("bla", 300),
                    new TestModel("Hohoho", 25)
            };
        }

        public static List<TestModel> getTestList() {
            return Arrays.asList(getTestArray());
        }
    }

    private static enum TestEnum { TEST1, TEST2, TEST3 };

    private static enum TestEnumEmpty {};

    @Test
    public void testFromArray() throws Exception {
        TestModel[] array = TestModel.getTestArray();
        Set<TestModel> possibleValues = new HashSet<>(asList(array));
        cycle(RandTestConstants.OBJS_CYCLES, () ->
            stream(RandTestConstants.RANDS)
                    .map(r -> r.from(array).val())
                    .forEach(tm -> assertTrue(possibleValues.contains(tm))));
    }

    @Test
    public void testFromArraySingleElement() throws Exception {
        String[] array = { "a" };
        cycle(RandTestConstants.OBJS_CYCLES, () ->
            stream(RandTestConstants.RANDS)
                    .map(r -> r.from(array).val())
                    .forEach(s -> assertTrue(s.equals(array[0]))));
    }

    @Test(expected = NullPointerException.class)
    public void testFromNullArray() throws Exception {
        TestModel[] array = null;
        RAND.from(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromEmptyArray() throws Exception {
        TestModel[] array = new TestModel[]{};
        RAND.from(array).val();
    }

    @Test
    public void testFromList() throws Exception {
        List<TestModel> list = TestModel.getTestList();
        Set<TestModel> possibleValues = new HashSet<>(list);
        cycle(RandTestConstants.OBJS_CYCLES, () ->
            stream(RandTestConstants.RANDS)
                    .map(r -> r.from(list).val())
                    .forEach(tm -> assertTrue(possibleValues.contains(tm))));
    }

    @Test
    public void testFrom1ElementList() throws Exception {
        List<String> list = Arrays.asList(new String[]{ "a" });
        cycle(RandTestConstants.OBJS_CYCLES, () ->
            stream(RandTestConstants.RANDS)
                    .map(r -> r.from(list).val())
                    .forEach(s -> assertTrue(s.equals(list.get(0)))));
    }

    @Test(expected = NullPointerException.class)
    public void testFromNullList() throws Exception {
        List<TestModel> array = null;
        RAND.from(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromEmptyList() throws Exception {
        List<TestModel> list = new ArrayList<>();
        RAND.from(list).val();
    }

    @Test
    public void testEnum() throws Exception {
        Set<TestEnum> possible = new HashSet<>(asList(TestEnum.values()));
        cycle(RandTestConstants.OBJS_CYCLES, () ->
            stream(RandTestConstants.RANDS)
                    .map(r -> r.from(TestEnum.class).val())
                    .forEach(tm -> assertTrue(possible.contains(tm))));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEnumEmpty() throws Exception {
        RAND.from(TestEnumEmpty.class).val();
    }

    @Test(expected = NullPointerException.class)
    public void testEnumNull() throws Exception {
        Class<? extends Enum> cls = null;
        RAND.from(cls).val();
    }

    @Test
    public void testFromKeys() throws Exception {
        Map<Integer, Integer> map = RAND.ints().mapKeys(25, RAND.ints()::val).val();
        cycle(RU_CYCLES, () ->
            stream(RANDS).forEach(r -> {
                int x = r.fromKeys(map).val();
                assertTrue(map.containsKey(x));
            }));
    }

    @Test(expected = NullPointerException.class)
    public void testFromKeysNullMap() throws Exception {
        Map<Integer, Integer> intMap = null;
        RAND.fromKeys(intMap).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromKeysEmptyMap() throws Exception {
        Map<Integer, Integer> intMap = new HashMap<>();
        RAND.fromKeys(intMap).val();
    }

    @Test
    public void testFromValues() throws Exception {
        Map<Integer, Integer> map = RAND.ints().mapKeys(25, RAND.ints()::val).val();
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    int x = r.fromValues(map).val();
                    assertTrue(map.containsValue(x));
                }));
    }

    @Test(expected = NullPointerException.class)
    public void testFromValuesNullMap() throws Exception {
        Map<Integer, Integer> intMap = null;
        RAND.fromValues(intMap).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromValuesEmptyMap() throws Exception {
        Map<Integer, Integer> intMap = new HashMap<>();
        RAND.fromValues(intMap).val();
    }

    /**********************
     * fromInts
     ***********************/

    @Test
    public void testFromIntsInteger() throws Exception {
        Integer[] array = RAND.ints().array(25).val();
        Set<Integer> arrayValues = new HashSet<>(asList(array));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    int x = r.fromInts(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromInts(array) instanceof RandUnitInt);
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsIntegerNullArray() throws Exception {
        Integer[] array = null;
        RAND.fromInts(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsIntegerEmptyArray() throws Exception {
        Integer[] array = new Integer[0];
        RAND.fromInts(array).val();
    }

    @Test
    public void testFromIntsInt() throws Exception {
        int[] array = RAND.ints().arrayPrimitive(25).val();
        Set<Integer> arrayValues = new HashSet<>(asList(toObject(array)));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    int x = r.fromInts(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromInts(array) instanceof RandUnitInt);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsIntEmtpyArray() throws Exception {
        int[] array = new int[0];
        RAND.fromInts(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsIntNullArray() throws Exception {
        int[] array = null;
        RAND.fromInts(array).val();
    }

    @Test
    public void testFromIntsList() throws Exception {
        List<Integer> list = RAND.ints().range(0,5).list(10).val();
        Set<Integer> listValues = new HashSet<>(list);
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    int x = r.fromInts(list).val();
                    assertTrue(listValues.contains(x));
                }));
        assertTrue(RAND.fromInts(list) instanceof RandUnitInt);
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsListNullList() throws Exception {
        List<Integer> list = null;
        RAND.fromInts(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsListNotEmpty() throws Exception {
        List<Integer> list = new ArrayList<>();
        RAND.fromInts(list).val();
    }

    @Test
    public void testFromIntsValues() {
        Map<Character, Integer> map = RAND.chars().letters().mapVals(25, RAND.ints()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                int x = r.fromIntsValues(map).val();
                assertTrue(map.containsValue(x));
            });
        });
        assertTrue(RAND.fromIntsValues(map) instanceof RandUnitInt);
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntsValuesNullMap() {
        Map<Collection, Integer> map = null;
        RAND.fromIntsValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntsValuesEmptyMap() {
        Map<String, Integer> map = new HashMap<>();
        RAND.fromIntsValues(map).val();
    }

    @Test
    public void testFromIntsKeys() {
        Map<Integer, Character> map = RAND.chars().letters().mapKeys(25, RAND.ints()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                int x = r.fromIntsKeys(map).val();
                assertTrue(map.containsKey(x));
            });
        });
        assertTrue(RAND.fromIntsKeys(map) instanceof RandUnitInt);
    }

    @Test(expected = NullPointerException.class)
    public void testFromIntKeysNullMap() {
        Map<Integer, ?> map = null;
        RAND.fromIntsKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromIntKeysEmptyMap() {
        Map<Integer, ?> map = new HashMap<>();
        RAND.fromIntsKeys(map).val();
    }

    /**********************
     * fromDoubles
     ***********************/

    @Test
    public void testFromDoublesDouble() throws Exception {
        Double[] array = RAND.doubles().array(25).val();
        Set<Double> arrayValues = new HashSet<>(asList(array));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    double x = r.fromDoubles(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromDoubles(array) instanceof RandUnitDouble);
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesDoubleNullArray() throws Exception {
        Double[] array = null;
        RAND.fromDoubles(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesDoubleEmptyArray() throws Exception {
        Double[] array = new Double[0];
        RAND.fromDoubles(array).val();
    }

    @Test
    public void testFromDoublesDoublePrim() throws Exception {
        double[] array = RAND.doubles().arrayPrimitive(25).val();
        Set<Double> arrayValues = new HashSet<>(asList(toObject(array)));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    double x = r.fromDoubles(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromDoubles(array) instanceof RandUnitDouble);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesDoublePrimEmtpyArray() throws Exception {
        double[] array = new double[0];
        RAND.fromDoubles(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesDoublePrimNullArray() throws Exception {
        double[] array = null;
        RAND.fromDoubles(array).val();
    }

    @Test
    public void testFromDoubleList() throws Exception {
        List<Double> list = RAND.doubles().range(0,5).list(10).val();
        Set<Double> listValues = new HashSet<>(list);
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    double x = r.fromDoubles(list).val();
                    assertTrue(listValues.contains(x));
                }));
        assertTrue(RAND.fromDoubles(list) instanceof RandUnitDouble);
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoubleListNullList() throws Exception {
        List<Double> list = null;
        RAND.fromDoubles(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesListNotEmpty() throws Exception {
        List<Double> list = new ArrayList<>();
        RAND.fromDoubles(list).val();
    }

    @Test
    public void testFromDoublesValues() {
        Map<Character, Double> map = RAND.chars().letters().mapVals(25, RAND.doubles()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                double x = r.fromDoublesValues(map).val();
                assertTrue(map.containsValue(x));
            });
        });
        assertTrue(RAND.fromDoublesValues(map) instanceof RandUnitDouble);
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoublesValuesNullMap() {
        Map<Collection, Double> map = null;
        RAND.fromDoublesValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesValuesEmptyMap() {
        Map<String, Double> map = new HashMap<>();
        RAND.fromDoublesValues(map).val();
    }

    @Test
    public void testFromDoublesKeys() {
        Map<Double, Character> map = RAND.chars().letters().mapKeys(25, RAND.doubles()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                double x = r.fromDoublesKeys(map).val();
                assertTrue(map.containsKey(x));
            });
        });
        assertTrue(RAND.fromDoublesKeys(map) instanceof RandUnitDouble);
    }

    @Test(expected = NullPointerException.class)
    public void testFromDoubleKeysNullMap() {
        Map<Double, ?> map = null;
        RAND.fromDoublesKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromDoublesKeysEmptyMap() {
        Map<Double, ?> map = new HashMap<>();
        RAND.fromDoublesKeys(map).val();
    }

    /**********************
     * fromLongs
     ***********************/

    @Test
    public void testFromLongsLong() throws Exception {
        Long[] array = RAND.longs().array(25).val();
        Set<Long> arrayValues = new HashSet<>(asList(array));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    long x = r.fromLongs(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromLongs(array) instanceof RandUnitLong);
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongNullArray() throws Exception {
        Long[] array = null;
        RAND.fromLongs(array).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsLongEmptyArray() throws Exception {
        Long[] array = new Long[0];
        RAND.fromLongs(array).val();
    }

    @Test
    public void testFromLongsLongPrim() throws Exception {
        long[] array = RAND.longs().arrayPrimitive(25).val();
        Set<Long> arrayValues = new HashSet<>(asList(toObject(array)));
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    long x = r.fromLongs(array).val();
                    assertTrue(arrayValues.contains(x));
                }));
        assertTrue(RAND.fromLongs(array) instanceof RandUnitLong);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsLongPrimEmtpyArray() throws Exception {
        long[] array = new long[0];
        RAND.fromLongs(array).val();
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongPrimNullArray() throws Exception {
        long[] array = null;
        RAND.fromLongs(array).val();
    }

    @Test
    public void testFromLongsList() throws Exception {
        List<Long> list = RAND.longs().range(0,5).list(10).val();
        Set<Long> listValues = new HashSet<>(list);
        cycle(RU_CYCLES, () ->
                stream(RANDS).forEach(r -> {
                    long x = r.fromLongs(list).val();
                    assertTrue(listValues.contains(x));
                }));
        assertTrue(RAND.fromLongs(list) instanceof RandUnitLong);
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsLongNullList() throws Exception {
        List<Long> list = null;
        RAND.fromLongs(list).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsListNotEmpty() throws Exception {
        List<Double> list = new ArrayList<>();
        RAND.fromDoubles(list).val();
    }

    @Test
    public void testFromLongsValues() {
        Map<Character, Long> map = RAND.chars().letters().mapVals(25, RAND.longs()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                long x = r.fromLongsValues(map).val();
                assertTrue(map.containsValue(x));
            });
        });
        assertTrue(RAND.fromLongsValues(map) instanceof RandUnitLong);
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsValuesNullMap() {
        Map<Collection, Long> map = null;
        RAND.fromLongsValues(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsValuesEmptyMap() {
        Map<String, Long> map = new HashMap<>();
        RAND.fromLongsValues(map).val();
    }

    @Test
    public void testFromLongsKeys() {
        Map<Long, Character> map = RAND.chars().letters().mapKeys(25, RAND.longs()::val).val();
        cycle(RU_CYCLES, () -> {
            stream(RANDS).forEach(r -> {
                long x = r.fromLongsKeys(map).val();
                assertTrue(map.containsKey(x));
            });
        });
        assertTrue(RAND.fromLongsKeys(map) instanceof RandUnitLong);
    }

    @Test(expected = NullPointerException.class)
    public void testFromLongsKeysNullMap() {
        Map<Long, ?> map = null;
        RAND.fromLongsKeys(map).val();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testFromLongsKeysEmptyMap() {
        Map<Long, ?> map = new HashMap<>();
        RAND.fromLongsKeys(map).val();
    }
}