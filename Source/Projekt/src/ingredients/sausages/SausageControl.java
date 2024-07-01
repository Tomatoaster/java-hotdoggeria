package ingredients.sausages;

public class SausageControl implements Runnable {
	private SausageModel sausage;
	private SausageView view;

	public SausageControl(SausageModel sausage, SausageView view) {
		this.sausage = sausage;
		this.view = view;
	}

	@Override
	public void run() {
		while (sausage.getSausage() != null && sausage.getSausage().getGrilledLevel() < 100) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				break;
				// System.out.println("Error: Running of Thread interrupted!");
			}

			int newGrill = sausage.getSausage().getGrilledLevel() + 1;
			sausage.getSausage().setGrilledLevel(newGrill);
			view.repaint();

		}
	}

	public void take() {
		sausage.setSausage(null);
		view.repaint();

	}
}
