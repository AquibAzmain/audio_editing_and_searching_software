package GUI;

import com.google.common.base.Charsets;
import com.google.common.io.Files;
import java.io.File;
import java.io.IOException;

public class LCS {
	public int lcs(String str1, String str2) {
		int l1 = str1.length()/1000;
		int l2 = str2.length()/1000;

		int[][] arr = new int[l1 + 1][l2 + 1];

		for (int i = l1 - 1; i >= 0; i--) {
			for (int j = l2 - 1; j >= 0; j--) {
				if (str1.charAt(i) == str2.charAt(j))
					arr[i][j] = arr[i + 1][j + 1] + 1;
				else
					arr[i][j] = Math.max(arr[i + 1][j], arr[i][j + 1]);
			}
		}

		int i = 0, j = 0;
		StringBuffer sb = new StringBuffer();
		while (i < l1 && j < l2) {
			if (str1.charAt(i) == str2.charAt(j)) {
				sb.append(str1.charAt(i));
				i++;
				j++;
			} else if (arr[i + 1][j] >= arr[i][j + 1])
				i++;
			else
				j++;
		}
		return sb.toString().length();
	}

	

	public int extractLCS (String one, String two) throws IOException{
		String a=Files.toString(new File(one), Charsets.UTF_8);
		String b=Files.toString(new File(two), Charsets.UTF_8);
		LCS obj = new LCS();
		int result = obj.lcs(a, b);
		return result;
	}
}
