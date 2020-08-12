package simpleracing.execute;

import static java.util.stream.Collectors.toList;

import java.util.List;
import java.util.stream.IntStream;

import simpleracing.input.CarRacingInitValue;
import simpleracing.input.CarRacingInput;
import simpleracing.output.CarRacingOutput;


public class CarRacingExecutor {

	private CarRacingInput carRacingInput;
	private CarGame racingGame;
	private CarRacingReferee referee;
	private CarRacingOutput carRacingOutput;

	public CarRacingExecutor() {
		this.carRacingInput = new CarRacingInput();
		this.racingGame = new CarRacingGame();
		this.referee = new CarRacingReferee();
		this.carRacingOutput = new CarRacingOutput();
	}

	public void execute() {
		CarRacingInitValue initValue = carRacingInput.input();
		List<Car> cars = this.generateCarsForRacing(initValue);
		this.playGame(cars, initValue.getTryCount());
		this.judgeRacingResult(cars);
		carRacingOutput.render();

	}

	private List<Car> generateCarsForRacing(CarRacingInitValue initValue) {
		return IntStream.range(0, initValue.getCarCount())
						.mapToObj(carNumber -> new Car(initValue.getNames()
																.get(carNumber)
																.trim()))
						.collect(toList());
	}

	private void playGame(List<Car> cars, int tryCount) {
		IntStream.range(0, tryCount)
				 .forEach(round -> {
					 racingGame.play(cars);
					 this.carRacingOutput.addSituation(cars);
				 });
	}

	private void judgeRacingResult(List<Car> cars) {
		referee.judge(cars);
		carRacingOutput.addWinnerContentBy(referee);
	}
}
