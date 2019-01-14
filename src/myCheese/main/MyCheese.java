package myCheese.main;


import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

import myCheese.io.PrintLables;

class MyCheese {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				try {

					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

					MainFrame mf = new MainFrame();
					mf.setVisible(true);
					// Печать при старте всех экземпляров этикеток
					//PrintLables pl = new PrintLables();
					//pl.print("labels//");
					mf.listeners();
				} catch (InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException
						| ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		});

	}
}
