package me.schf.ai.tunnel.web.util;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Util {

	private Util() {
		super();
	}

	private static final Random RANDOM = new Random();
	private static final List<String> STARTING_POINTS = List.of("apple", "car", "book", "river", "mountain", "cloud",
			"house", "dog", "cat", "city", "computer", "ocean", "island", "tree", "flower", "bottle", "camera", "phone",
			"desk", "chair", "shoe", "door", "window", "pencil", "clock", "watch", "shirt", "lamp", "bridge", "road",
			"train", "airplane", "boat", "bicycle", "forest", "desert", "moon", "sun", "star", "planet", "keyboard",
			"mouse", "screen", "cup", "plate", "fork", "spoon", "knife", "bed", "pillow", "blanket", "hat", "glove",
			"sock", "mirror", "wallet", "key", "ring", "brush", "soap", "toothbrush", "toothpaste", "towel", "bag",
			"backpack", "tent", "map", "compass", "pen", "marker", "notebook", "magazine", "newspaper", "painting",
			"statue", "coin", "dollar", "ticket", "receipt", "menu", "banana", "grape", "peach", "pear", "plum",
			"carrot", "potato", "onion", "lettuce", "cucumber", "bicycle", "skateboard", "helmet", "glasses", "fan",
			"heater", "radio", "television", "projector", "speaker");

	public static List<String> randomStartingPoints() {
		Set<String> result = new HashSet<>();

		int targetSize = 5;

		while (result.size() < targetSize) {
			String randomWord = STARTING_POINTS.get(RANDOM.nextInt(STARTING_POINTS.size()));
			result.add(randomWord);
		}

		return new ArrayList<>(result);
	}

}
