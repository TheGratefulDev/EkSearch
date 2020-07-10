package coffee.ka.eksearch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class EkSearch {

	public interface OnEachWordCompletedListener {
		void onEachWordCompleted(List<Integer> indices);
	}

	private TrieNode<Integer> trieNode;

	EkSearch(EkSearchBuilder ekSearchBuilder){
		this.trieNode = ekSearchBuilder.trieNode;
	}

	public void searchPrefix(String prefix, OnEachWordCompletedListener onEachWordCompletedListener){

		HashSet<Integer> searchResultIndices = new HashSet<>(trieNode.searchPrefix(prefix));

		List<Integer> indices = new ArrayList<>(searchResultIndices);

		if(onEachWordCompletedListener!=null){
			onEachWordCompletedListener.onEachWordCompleted(indices);
		}
	}


	/**
	 * Builder
	 */
	public static class EkSearchBuilder {

		public TrieNode<Integer> getTrieNode() {
			return trieNode;
		}

		private TrieNode<Integer> trieNode = new TrieNode<>('*', false);

		public EkSearchBuilder(){ };

		public EkSearchBuilder addWordToNode(int i , String... strings) throws Exception {
			for (String s: strings){
				trieNode.addWord(s, i);
			}
			return this;
		}

		public EkSearchBuilder addWordsToNode(int i , String... strings) throws Exception {
			for (String s: strings){
				String[] sSplit = s.split("\\s+");
				trieNode.addAllWords(sSplit, i, true);
			}
			return this;
		}

		public EkSearch build(){
			return new EkSearch(this);
		}


	}

}
