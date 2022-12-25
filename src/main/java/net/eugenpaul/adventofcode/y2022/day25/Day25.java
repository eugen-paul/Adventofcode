package net.eugenpaul.adventofcode.y2022.day25;

import java.util.List;
import java.util.logging.Level;
import java.util.stream.Collectors;

import lombok.Getter;
import net.eugenpaul.adventofcode.helper.SolutionTemplate;

public class Day25 extends SolutionTemplate {

	@Getter
	private long part1;
	@Getter
	private long part2;

	public static void main(String[] args) {
		Day25 puzzle = new Day25();
		puzzle.doPuzzleFromFile("y2022/day25/puzzle1.txt");
	}

	@Override
	public boolean doEvent(List<String> eventData) {

		part1 = doPuzzle1(eventData);
		part2 = doPuzzle2(eventData, (int) part1);

		logger.log(Level.INFO, () -> "part1 : " + getPart1());
		logger.log(Level.INFO, () -> "part2 : " + getPart2());

		return true;
	}

	private long doPuzzle1(List<String> eventData) {
		List<Long> digs = eventData.stream()//
				.map(this::snafToDigimal)//
				.collect(Collectors.toList());

		digs.forEach(System.out::println);

		long sum = digs.stream().mapToLong(v -> v).sum();

		System.out.println("sum = " + sum + "");

		digitToSnaf(1);
		digitToSnaf(3);
		digitToSnaf(5);
		digitToSnaf(10);

		String sn = digitToSnaf(sum);

		System.out.println(sn);

		return 1;
	}

	private long doPuzzle2(List<String> eventData, int steps1) {
		return 1;
	}

	private long snafToDigimal(String data) {
		long current = 0;
		int pos = 5;

		for (int i = 0; i < data.length(); i++) {
			switch (data.charAt(i)) {
			case '=':
				current = current * pos - 2;
				break;
			case '-':
				current = current * pos - 1;
				break;
			case '0':
				current = current * pos;
				break;
			case '1':
				current = current * pos + 1;
				break;
			case '2':
				current = current * pos + 2;
				break;

			default:
				break;
			}
		}
		return current;
	}

	private String digitToSnaf(long data) {
		String response = "";
		long dig = data;
		while (dig > 0) {
			int mod = (int) (dig % 5);
			switch (mod) {
			case 0:
				response = '0' + response;
				break;
			case 1:
				response = '1' + response;
				break;
			case 2:
				response = '2' + response;
				break;
			case 3:
				response = '=' + response;
				break;
			case 4:
				response = '-' + response;
				break;
			default:
				break;
			}

			dig = (dig + 2) / 5;
		}
		return response;
	}

}
