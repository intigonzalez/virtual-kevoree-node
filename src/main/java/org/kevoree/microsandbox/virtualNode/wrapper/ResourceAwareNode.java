package org.kevoree.microsandbox.virtualNode.wrapper;

import org.kevoree.annotation.*;
import org.kevoree.api.BootstrapService;
import org.kevoree.api.Context;
import org.kevoree.api.ModelService;
import org.kevoree.library.defaultNodeTypes.JavaNode;
import org.kevoree.library.defaultNodeTypes.wrapper.WrapperFactory;
import org.kevoree.log.Log;

@NodeType
@Library(name = "Java")
public class ResourceAwareNode extends JavaNode {

    @KevoreeInject
    public ModelService modelService = null;

    @KevoreeInject
    public BootstrapService bootstrapService = null;

    @KevoreeInject
    Context context;

    @Start
    public void startNode() {
        super.startNode();
        Log.info("Node : {} was started", context.getNodeName());
        System.out.printf("LALALALLALALLALALALLA %s\n", context.getNodeName());
    }

    @Stop
    public void stopNode() {
        super.stopNode();
    }

    @Override
    protected WrapperFactory createWrapperFactory(String nodeName) {
        Log.info("Requesting Wrapper factory to node : {}", context.getNodeName());
        return new ResourceAwareWrapperFactory(nodeName,modelRegistry);
    }
}



