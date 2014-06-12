package org.kevoree.microsandbox.virtualNode.wrapper

import org.kevoree.library.defaultNodeTypes.ModelRegistry
import org.kevoree.library.defaultNodeTypes.wrapper.WrapperFactory
import org.kevoree.api.BootstrapService
import org.kevoree.modeling.api.KMFContainer
import org.kevoree.ContainerNode
import org.kevoree.library.defaultNodeTypes.wrapper.KInstanceWrapper
import org.kevoree.api.ModelService

public class ResourceAwareWrapperFactory(nodeName: String, val modelRegistry: ModelRegistry) : WrapperFactory(nodeName) {

    override fun wrap(modelElement: KMFContainer, newBeanInstance: Any, tg: ThreadGroup, bs: BootstrapService,modelService: ModelService): KInstanceWrapper = when(modelElement) {
        is ContainerNode -> {
            ResourceAwareNodeWrapper(modelElement, newBeanInstance, tg, bs, modelService, modelRegistry)
        }
        else -> {
            super.wrap(modelElement, newBeanInstance, tg, bs, modelService)
        }
    }

}