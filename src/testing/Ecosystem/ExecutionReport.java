package testing.Ecosystem;


/*import java.io.File;

import org.tmatesoft.svn.core.SVNCommitInfo;
import org.tmatesoft.svn.core.SVNDepth;
import org.tmatesoft.svn.core.SVNURL;
import org.tmatesoft.svn.core.auth.ISVNAuthenticationManager;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepository;
import org.tmatesoft.svn.core.internal.io.dav.DAVRepositoryFactory;
import org.tmatesoft.svn.core.wc.SVNClientManager;
import org.tmatesoft.svn.core.wc.SVNCommitClient;
import org.tmatesoft.svn.core.wc.SVNRevision;
import org.tmatesoft.svn.core.wc.SVNUpdateClient;
import org.tmatesoft.svn.core.wc.SVNWCUtil;

*/

public class ExecutionReport{
	
	
	
	
	/*GenericFunctionsPack.LinuxFunctions lx;
	
	public static void main(String[] args){
			try {
				String url = "http://172.16.3.215/intranetnew/intranetn/Naukri_International/QA/Aarti/";
				File file = new File("http://172.16.3.215/intranetnew/intranetn/Naukri_International/QA/Aarti/");

				DAVRepositoryFactory.setup();
				SVNURL svnurl = SVNURL.parseURIDecoded(url);
				SVNCommitInfo commitInfo;
				String uname = "vipul.goyal"; // auth details to connect to SVN repo
				String pwd = "naukri5467";

				System.out.println("<br> SVN URL is : "+ svnurl); //pw is a reference object of response.getWriter()
				DAVRepository repo = (DAVRepository) DAVRepositoryFactory.create(svnurl);
				ISVNAuthenticationManager authManager = SVNWCUtil.createDefaultAuthenticationManager(uname, pwd);
				repo.setAuthenticationManager(authManager);

				long latestRevision = repo.getLatestRevision();
				System.out.println("<br> LATEST REVISION IS : "+latestRevision);
				
				SVNClientManager clientManager = SVNClientManager.newInstance(null,repo.getAuthenticationManager());
				try {
				SVNUpdateClient uc = clientManager.getUpdateClient();
				uc.doUpdate(file, SVNRevision.HEAD, true);
				SVNCommitClient cc = clientManager.getCommitClient();
				commitInfo = cc.doCommit(new File[] {file}, false, "Updated regex", null, null, false, false,SVNDepth.INFINITY);
				System.out.println("<br> COMMIT INFORMATION : "+commitInfo);
				} finally {
				clientManager.dispose();
				}
				
				} catch (Exception e) {
				System.out.println(e.getMessage());
				}
	}
	
	public void CreateReport(){
		String prevFolderCount = "";
		lx = new GenericFunctionsPack.LinuxFunctions();
		//new File(<directory path>).listFiles().length   -- fetch number of folder using JAVA
		try{
			prevFolderCount = lx.executeCron("cd /home/developer876/htdocs/ni/seleniumresultsheet/previousExecution/;ll | grep ^d | wc -l");
			System.out.println("Vipul"+prevFolderCount);
			lx.executeCron("cp c:\\freefallprotection.txt vipul.goyal:vipul@passi@172.16.3.231:/home/developer876/htdocs/ni/seleniumresultsheet/previousExecution/");
		}catch(Exception e){
			e.printStackTrace();
		}
	}
*/	
}
