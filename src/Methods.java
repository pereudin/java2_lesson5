public class Methods {
    static final int size = 1000000;
    static final int h = size / 2;

    public static void method1() {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        for (int i = 0; i < size; i++) {
            arr[i] = (float)(arr[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
        }
        long b = System.currentTimeMillis();
        System.out.println("method1. Время, затраченное на цикл расчета: " + (b - a));
    }

    public static void method2() {
        float[] arr = new float[size];
        for (int i = 0; i < arr.length; i++) {
            arr[i] = 1;
        }
        long a = System.currentTimeMillis();

        float[] arr1 = new float[h];
        float[] arr2 = new float[h];

        System.arraycopy(arr, 0, arr1, 0, h);
        System.arraycopy(arr, h, arr2, 0, h);

        long b = System.currentTimeMillis();
        System.out.println("method2. Время, затраченное на разбивку массива arr на 2 массива (arr1 и arr2): " + (b - a));

        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < h; i++) {
                    arr1[i] = (float)(arr1[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
                }
                long c = System.currentTimeMillis();
                System.out.println("method2. Время, затраченное на расчет arr1: " + (c - b));
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < h; i++) {
                arr2[i] = (float)(arr2[i] * Math.sin(0.2f + i / 5) * Math.cos(0.2f + i / 5) * Math.cos(0.4f + i / 2));
            }
            long d = System.currentTimeMillis();
            System.out.println("method2. Время, затраченное на расчет arr2: " + (d - b));
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long e = System.currentTimeMillis();

        System.arraycopy(arr1, 0, arr, 0, h);
        System.arraycopy(arr2, 0, arr, h, h);

        long f = System.currentTimeMillis();
        System.out.println("method2. Время, затраченное на склейку: " + (f - e));
    }
}
