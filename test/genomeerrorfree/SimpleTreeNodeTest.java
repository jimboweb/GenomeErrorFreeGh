/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package genomeerrorfree;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author jimstewart
 */
public class SimpleTreeNodeTest {
    
    public SimpleTreeNodeTest() {
    }

    /**
     * Test of addChildNode method, of class SimpleTreeNode.
     */
    @Test
    public void testAddChildNode() {
        System.out.println("addChildNode");
        OverlapGraph.SuffixOverlap overlapLink = null;
        SimpleTreeNode instance = new SimpleTreeNode();
        instance.addChildNode(overlapLink);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addAllChildNodes method, of class SimpleTreeNode.
     */
    @Test
    public void testAddAllChildNodes() {
        System.out.println("addAllChildNodes");
        ArrayList<OverlapGraph.SuffixOverlap> overlaps = null;
        SimpleTreeNode instance = new SimpleTreeNode();
        instance.addAllChildNodes(overlaps);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pruneDescendantsAtDepth method, of class SimpleTreeNode.
     */
    @Test
    public void testPruneDescendantsAtDepth() {
        System.out.println("pruneDescendantsAtDepth");
        int depth = 0;
        SimpleTreeNode instance = new SimpleTreeNode();
        instance.pruneDescendantsAtDepth(depth);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDescendantsAtDepth method, of class SimpleTreeNode.
     */
    @Test
    public void testGetDescendantsAtDepth() {
        System.out.println("getDescendantsAtDepth");
        int depth = 0;
        SimpleTreeNode instance = new SimpleTreeNode();
        ArrayList<SimpleTreeNode> expResult = null;
        ArrayList<SimpleTreeNode> result = instance.getDescendantsAtDepth(depth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllChildrenOfNodes method, of class SimpleTreeNode.
     */
    @Test
    public void testGetAllChildrenOfNodes() {
        System.out.println("getAllChildrenOfNodes");
        ArrayList<SimpleTreeNode> nodes = null;
        SimpleTreeNode instance = new SimpleTreeNode();
        ArrayList<SimpleTreeNode> expResult = null;
        ArrayList<SimpleTreeNode> result = instance.getAllChildrenOfNodes(nodes);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addChildrenAtDepth method, of class SimpleTreeNode.
     */
    @Test
    public void testAddChildrenAtDepth() {
        System.out.println("addChildrenAtDepth");
        int depth = 0;
        ArrayList<ArrayList<OverlapGraph.SuffixOverlap>> children = null;
        SimpleTreeNode instance = new SimpleTreeNode();
        instance.addChildrenAtDepth(depth, children);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of pruneChildNodesToOne method, of class SimpleTreeNode.
     */
    @Test
    public void testPruneChildNodesToOne() {
        System.out.println("pruneChildNodesToOne");
        SimpleTreeNode rootNode = null;
        OverlapGraph gr = null;
        boolean[] usedNodes = null;
        SimpleTreeNode instance = new SimpleTreeNode();
        instance.pruneChildNodesToOne(rootNode, gr, usedNodes);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
