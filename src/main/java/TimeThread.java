class TimeThread implements Runnable {
    private Thread t;
    private Board newGame;

    TimeThread(Board newGame) {
        this.newGame = newGame;
    }

    public void run() {
        while (true) {
            try {
                Thread.sleep(1000);
                newGame.timer();
            } catch (InterruptedException e) {
                System.exit(0);
            }
        }
    }

    public void start() {
        if (t == null) {
            t = new Thread(this);
            t.start();
        }
    }
}
