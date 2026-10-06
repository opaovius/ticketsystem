package com.chris.ticket.Util;

import java.util.Random;

public class PasswordGen {

	public static String generate(int length) {
		
		int curr = 0;
		int coin = 0;
		Random rand = new Random(System.currentTimeMillis());
		String pass = "";

		for (int x = 0; x < length; x++) {

			int pos = rand.nextInt(3);

			switch (pos) {
			//Buchstaben
			case 0:

				coin = rand.nextInt(2);

				switch (coin) {
				case 0:
					
					curr = rand.nextInt(26) + 65;
					pass = pass + (char) curr;
					break;
					
				default:
					
					curr = rand.nextInt(26) + 97;
					pass = pass + (char) curr;

				}

				break;
			//Zahlen
			case 1:
				
				curr = rand.nextInt(10);
				pass = pass + curr;
				
				break;
			//Sonderzeichen
			default:

				coin = rand.nextInt(2);
				
				switch(coin) {
				case 0:
					curr = rand.nextInt(15) + 33;
					pass = pass + (char) curr;
					break;
				default:
					curr = rand.nextInt(7) + 58;
					pass = pass + (char) curr;
				}
			}

		}

		return pass;

	}

}
