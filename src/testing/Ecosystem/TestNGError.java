package testing.Ecosystem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.IMethodInstance;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;



public class TestNGError implements IInvokedMethodListener {
	WebDriver driver;
	private static Map<ITestResult, List<Throwable>> verificationFailuresMap = new HashMap<ITestResult, List<Throwable>>();
	
	
	@Override
	public void afterInvocation(IInvokedMethod method, ITestResult result) {
		testing.Ecosystem.TestData.TestCaseId = result.getName();
		Reporter.setCurrentTestResult(result);
			
		if (method.isTestMethod()) {
			List<Throwable> verificationFailures = TestNGAssertionsCustom.getVerificationFailures();
			
			//if there are verification failures...
			if (verificationFailures.size() > 0) {
				//set the test to failed
				result.setStatus(ITestResult.FAILURE);

				//if there is an assertion failure add it to verificationFailures
				if (result.getThrowable() != null) {
					verificationFailures.add(result.getThrowable());
				}
				
				int size = verificationFailures.size();
				//if there's only one failure just set that
				if (size == 1) {
					result.setThrowable(verificationFailures.get(0));
				} else {
					//create a failure message with all failures and stack traces (except last failure)
					StringBuffer failureMessage = new StringBuffer("Multiple failures (").append(size).append("):\n\n");
					for (int i = 0; i < size-1; i++) {
						failureMessage.append("Failure ").append(i+1).append(" of ").append(size).append(":\n");
						Throwable t = verificationFailures.get(i);
						String fullStackTrace = Utils.stackTrace(t, false)[1];
						failureMessage.append(fullStackTrace).append("\n\n");
						
					}
					
					//final failure
					Throwable last = verificationFailures.get(size-1);
					failureMessage.append("Failure ").append(size).append(" of ").append(size).append(":\n");
					failureMessage.append(last.toString());
					
					//set merged throwable
					Throwable merged = new Throwable(failureMessage.toString());
					merged.setStackTrace(last.getStackTrace());
					
					result.setThrowable(merged);
				}
			}
		}
	}

	@Override
	public void beforeInvocation(IInvokedMethod method, ITestResult result) {
		/*String browserType="FF";
		int count = 0;
		String testCaseName = result.getName();
		GenericFunctionsPack.GenericFunctions generic = new GenericFunctionsPack.GenericFunctions(driver);
		datatable.Xls_Reader xls = new datatable.Xls_Reader(xslt.TestData.xlsPath);
		for(int i=2;i<=xls.getRowCount("TestCaseSheet");i++){
			if(testCaseName.equalsIgnoreCase(xls.getCellData("TestCaseSheet", "TestCaseID", i))){
				browserType = xls.getCellData("TestCaseSheet", "Browser", i);
				count = i;
				break;
		}
	}
		if(browserType.equals(""))
			browserType = "FF";
		
		driver = generic.StartDriver(browserType);
	*/	
	}
}
