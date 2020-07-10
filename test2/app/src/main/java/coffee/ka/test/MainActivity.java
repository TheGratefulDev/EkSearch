package coffee.ka.test;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import coffee.ka.eksearch.EkSearch;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		final List<Test> tests = getAListOfTestObjects();

		EkSearch.EkSearchBuilder ekSearchBuilder = new EkSearch.EkSearchBuilder();
		for (int i = 0; i <  tests.size(); i++) {
			try {
				Test individualObject = tests.get(i);

				ekSearchBuilder.addWordsToNode(i, individualObject.getDescription().toLowerCase());
				ekSearchBuilder.addWordToNode(i, individualObject.getName().toLowerCase());
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}

		EkSearch ekSearch = ekSearchBuilder.build();


		ekSearch.searchPrefix("he", new EkSearch.OnEachWordCompletedListener() {
			@Override
			public void onEachWordCompleted(List<Integer> indices) {
				for (int i : indices) {
					Log.d("Test" , tests.get(i) .getName());
				}
			}
		});

	}

	private List<Test> getAListOfTestObjects(){
		final List<Test> tests = new ArrayList<>();
		tests.add(new Test("A Hello", "World"));
		tests.add(new Test("B World", "Kitty"));
		tests.add(new Test("C 1", "The world is funny and Hello"));
		tests.add(new Test("D eell2o", "highway to Hell"));
		tests.add(new Test("E xl3lo", "This is not ehhe"));
		tests.add(new Test("F Hel4lo", "What Woudl  you do? "));

		return tests;
	}



}