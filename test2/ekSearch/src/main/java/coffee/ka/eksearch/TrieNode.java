package coffee.ka.eksearch;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

// Link: https://www.topcoder.com/community/data-science/data-science-tutorials/using-tries/
// https://medium.freecodecamp.com/trie-prefix-tree-algorithm-ee7ab3fe3413
class TrieNode<T> {

	char c;
	private HashMap<Character, TrieNode<T>> children = new HashMap<Character, TrieNode<T>>();
	private boolean terminating = false;
	private ArrayList<T> objectList = new ArrayList<T>();
	private TrieNode<T> parent = null;

	public TrieNode(char c, boolean terminating) {
		this.c = c;
		this.terminating = terminating;
	}

	public void addAllWords(String[] words, T obj, boolean continuous) throws Exception {

		if(continuous) {
			for(int i = 0; i < words.length; i++) {
				String str = EkStringUtils.join(Arrays.copyOfRange(words, i, words.length), " ");
				addWord(str, obj);
			}
		}else{
			for (String word : words) {
				addWord(word, obj);
			}
		}

	}

	public void addWord(String word, T obj) throws Exception{
		int i = 0;
		TrieNode<T> parent = this;
		addWord(word, obj, parent, i);
	}

	private void addWord(String word, T obj, TrieNode<T> parent, int i) throws Exception{
		if(i >= word.length()) {
			parent.setTerminating(true);
			parent.addToObjectList(obj);
		}
		else{
			char current_char = word.charAt(i);

			if(!parent.getChildren().containsKey(current_char)) {
				TrieNode<T> trie = new TrieNode<T>(current_char, false);
				parent.getChildren().put(current_char, trie);
				trie.setParent(parent);
				i++;
				addWord(word, obj, trie, i);
			}

			else {
				TrieNode<T> trie = parent.getChildren().get(current_char);
				i++;
				addWord(word, obj, trie, i);
			}
		}
	}

	public ArrayList<T> searchPrefix(String prefix) {
		TrieNode<T> parent = this;
		int i = 0;
		return searchPrefix(prefix, i, parent);
	}

	private ArrayList<T> searchPrefix(String prefix, int i, TrieNode<T> parent) {

		if(i == prefix.length()) {
			return getAll(parent);
		} else {
			char current_char = prefix.charAt(i);

			if(parent.getChildren().containsKey(current_char)) {

				TrieNode<T> trie = parent.getChildren().get(current_char);
				i++;
				return searchPrefix(prefix, i, trie);
			}

			else {
				return new ArrayList<T>();
			}
		}

	}

	private ArrayList<T> getAll(TrieNode<T> trie) {

		if(trie.isTerminating()) {

			if(!trie.getChildren().isEmpty()) {
				ArrayList<T> result = trie.getObjectList();

				for(Map.Entry<Character, TrieNode<T>> entry : trie.getChildren().entrySet()){
					TrieNode<T> child = entry.getValue();
					result.addAll(getAll(child));
				}
				return result;

			} else
				return trie.getObjectList();
		}

		else {
			ArrayList<T> result = new ArrayList<T>();
			Iterator<Map.Entry<Character, TrieNode<T>>> iter = trie.getChildren().entrySet().iterator();

			while(iter.hasNext()) {
				Map.Entry<Character, TrieNode<T>> entry = iter.next();

				TrieNode<T> child  = entry.getValue();
				result.addAll(getAll(child));

			}
			return result;
		}

	}

	public void addToObjectList(T obj) throws Exception {
		if(terminating) {
			objectList.add(obj);
		}
		else
			throw new Exception("Cannot add object to non-terminating node");
	}

	public ArrayList<T> getObjectList() {
		return objectList;
	}

	public HashMap<Character, TrieNode<T>> getChildren() {
		return children;
	}

	public void setTerminating(boolean terminating) {
		this.terminating = terminating;
	}

	public boolean isTerminating() {
		return terminating;
	}

	public void setParent(TrieNode<T> parent) {
		this.parent = parent;
	}

	public TrieNode<T> getParent() {
		return parent;
	}

}

