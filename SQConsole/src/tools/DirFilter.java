package tools;

import java.io.File;
import java.io.FilenameFilter;

public class DirFilter implements FilenameFilter {

	private String type;

	public DirFilter(String tp){
		this.type=tp;
	}

	public boolean accept(File dir, String name) {
		File file = new File(name);
		String filename=file.getName();
		return filename.indexOf(type)!=-1;
	}

}
