package org.jboss.windup.web.selenium;

import java.awt.AWTException;
import java.util.ArrayList;

import junit.framework.TestCase;

public class Selenium04Test extends TestCase {

	private AnalyzeProject selenium;

	public void setUp() throws InterruptedException {
		selenium = new AnalyzeProject();
	}

	public void testStep01() throws InterruptedException, AWTException {
		/*
		 * Step 1
		 */
		assertEquals("Application List", selenium.headerTitle());
		assertEquals("Application List", selenium.pageTitle());

		selenium.switchTab(2);
		assertEquals("All Issues", selenium.pageTitle());

		selenium.switchTab(3);
		assertEquals("Technologies", selenium.pageTitle());

		selenium.switchTab(4);
		assertEquals("Dependencies", selenium.pageTitle());

		selenium.switchTab(5);
		assertEquals("About", selenium.pageTitle());

		selenium.switchTab(1);
		selenium.clickSendFeedback();
		/*
		 * Step 2
		 */
		selenium.closeFeedback();
		selenium.closeDriver();
	}

	public void testAppList() throws AWTException {
		assertEquals("Application List", selenium.headerTitle());
		assertEquals("Application List", selenium.pageTitle());

		// Steps 3 - 4
		assertTrue(selenium.applicationSort());

		// Step 5
		selenium.filterAppList("Name", "ad");
		assertEquals("[AdditionWithSecurity-EAR-0.01.ear, AdministracionEfectivo.ear]", selenium.listApplications().toString());

		// Step 6
		selenium.filterAppList("Tags", "JPA XML 2.0");
		assertEquals("[AdministracionEfectivo.ear]", selenium.listApplications().toString());

		// Step 7
		selenium.changeRelationship("Matches any filter (OR)");
		assertEquals("[AdditionWithSecurity-EAR-0.01.ear, AdministracionEfectivo.ear]", selenium.listApplications().toString());
		
		// Step 8
		selenium.clearFilters();
		assertEquals("[AdditionWithSecurity-EAR-0.01.ear, arit-ear-0.8.1-SNAPSHOT.ear, AdministracionEfectivo.ear]",
				selenium.listApplications().toString());

		// Step 9
		selenium.filterAppList("Tags", "JPA XML 2.0");
		assertEquals("[AdministracionEfectivo.ear]", selenium.listApplications().toString());
		
		// Step 10
		assertTrue(selenium.deleteFilter("JPA XML 2.0"));
		assertEquals("[AdditionWithSecurity-EAR-0.01.ear, arit-ear-0.8.1-SNAPSHOT.ear, AdministracionEfectivo.ear]",
				selenium.listApplications().toString());

		selenium.closeDriver();
	}

	public void testStep02() {
		selenium.switchTab(2);
		
		// Step 11
		assertEquals("All Issues", selenium.pageTitle());
		assertEquals("[Migration Optional, Cloud Mandatory, Cloud Optional]", selenium.allIssuesReport().toString());

		// Step 12
		assertTrue(selenium.sortAllIssues());
		
		// Step 13
		assertTrue(selenium.clickFirstIssue());
		
		// Step 14
		selenium.clickShowRule();
		
		// Step 15
		selenium.goBack();
		assertFalse(selenium.showRuleVisible());

		selenium.closeDriver();
	}

	public void testStep03() throws InterruptedException {
		// Step 16
		selenium.switchTab(3);
		assertEquals("Technologies", selenium.pageTitle());

		// Step 17
		// should be assertTrue but does not work on the page
		assertTrue(selenium.techApps());
		assertTrue(selenium.clickTechApp());
		// need to add stuff to check that there is stuff on Technologies page

		// Step 18
		selenium.goBack();
		assertEquals("Technologies", selenium.pageTitle());

		selenium.closeDriver();
	}

	public void testStep04() throws InterruptedException, AWTException {
		// Step 19
		selenium.switchTab(4);
		assertEquals("Dependencies", selenium.pageTitle());
		
		// Step 20
		String hash = selenium.clickMavenCoord();

		Thread.sleep(1000);
		selenium.navigateTo(2);
		selenium.mavenSearch(hash);
		assertTrue(selenium.checkURL().startsWith("http://search.maven.org"));
		selenium.navigateTo(1);

		selenium.closeDriver();
	}

	public void testStep05() {
		// Step 21
		selenium.switchTab(5);
		assertEquals("About", selenium.pageTitle());

		ArrayList<String> links = selenium.getAboutLinks();
		ArrayList<String> aboutLinks = new ArrayList<String>();
		aboutLinks.add("https://twitter.com/jbosswindup");
		aboutLinks.add("https://developers.redhat.com/products/rhamt/overview/");
		aboutLinks.add("https://access.redhat.com/documentation/en-us/red_hat_application_migration_toolkit/");
		aboutLinks.add("https://github.com/windup/windup");
		aboutLinks.add("https://github.com/windup/windup/wiki");
		aboutLinks.add("https://developer.jboss.org/en/windup?view=discussions");
		aboutLinks.add("https://lists.jboss.org/mailman/listinfo/windup-dev");
		aboutLinks.add("https://issues.jboss.org/browse/WINDUP");

		assertTrue(links.equals(aboutLinks));
		selenium.closeDriver();
	}

	public void testStep06() throws InterruptedException, AWTException {
		selenium.switchTab(5);
		assertEquals("About", selenium.pageTitle());
		
		//Step 39
		selenium.clickSendFeedback();
		selenium.moveToFeedback();
		
		//Step 40
		selenium.selectFeedbackButton("awesome");
		assertTrue(selenium.checkFeedbackButton("awesome"));
		
		// Step 41
		selenium.selectFeedbackButton("good");
		assertFalse(selenium.checkFeedbackButton("awesome"));
		assertTrue(selenium.checkFeedbackButton("good"));
		
		// Step 42
		selenium.submitFeedback();
		assertTrue(selenium.submitError());
		
		// Step 43
		selenium.populateTextBox();
		String path = "/home/edixon/Pictures/RHAMT-WebUI_Screenshot.png";
		selenium.feedbackAttachFile(path);
		
		// Step 46
		selenium.feedbackPopulateEmail("email");
		selenium.feedbackPopulateName("name");
		assertTrue(selenium.popupRemoved("atlwdg-blanket"));
		
		selenium.closeDriver();
	}

}
