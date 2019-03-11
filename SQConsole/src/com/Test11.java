package com;

public class Test11 {
	
	//synchronized修饰非静态方法     对象锁
	public synchronized void method1() throws InterruptedException{
		for (int i = 0; i <3; i++) {
            Thread.sleep(1000);
            System.out.println("method1 running...");
        }
		
	}
	
	//synchronized修饰静态方法      类锁
	public static synchronized void method2() throws InterruptedException{
		for (int i = 0; i <3; i++) {
            Thread.sleep(1000);
            System.out.println("method2 running...");
        }
	}

	/**
	 * 结论：类锁和对象锁不同，它们之间不会产生互斥。同为类锁或同为对象锁的时候,它们之间会产生排斥,
	 *       一但某个进程抢到锁之后,其它的进程只能排队等待。
	 * 1. 当synchronized修饰一个static方法时，多线程下，获取的是类锁
	 * （即Class本身，注意：不是实例），作用范围是整个静态方法，作用的对象是这个类的所有对象。
	 * 2. 当synchronized修饰一个非static方法时，多线程下，获取的是对象锁（即类的实例对象），
	 * 作用范围是整个方法，作用对象是调用该方法的对象。
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		
		final Test11 te = new Test11();
		
		Thread th1 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					method2();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		});
		
		
		Thread th2 = new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					te.method1();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
			}
		});
		
		th1.start();
		th2.start();
		
	}
	
}
