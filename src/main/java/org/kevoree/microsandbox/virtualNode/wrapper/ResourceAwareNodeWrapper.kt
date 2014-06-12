package org.kevoree.microsandbox.virtualNode.wrapper

import org.kevoree.modeling.api.KMFContainer
import org.kevoree.api.BootstrapService
import org.kevoree.api.ModelService
import org.kevoree.library.defaultNodeTypes.ModelRegistry
import org.kevoree.library.defaultNodeTypes.wrapper.KInstanceWrapper
import org.kevoree.ContainerRoot
import org.kevoree.log.Log
import org.kevoree.Instance
import org.kevoree.library.defaultNodeTypes.reflect.MethodAnnotationResolver
import org.kevoree.annotation.KevoreeInject

class ResourceAwareNodeWrapper(val modelElement: KMFContainer,
                               override val targetObj: Any,
                               override var tg: ThreadGroup,
                               override val bs: BootstrapService,
                               val ms: ModelService,
                               val modelRegistry: ModelRegistry) : KInstanceWrapper {

    override var isStarted: Boolean = false
    override var resolver: MethodAnnotationResolver = MethodAnnotationResolver(javaClass)

    override fun kInstanceStart(tmodel: ContainerRoot): Boolean {
        val m = modelElement as Instance
        Log.info("\tINTI - Calling start for {}", m.name)

        resolver = MethodAnnotationResolver(targetObj.javaClass)
        val met = resolver.resolve(javaClass<org.kevoree.annotation.Start>())
        met?.invoke(targetObj)
        return true
    }

    override fun kInstanceStop(tmodel: ContainerRoot): Boolean {
        return true
    }

    override fun create() {
        val m = modelElement as Instance
        val name = m.name!!;
        Log.info("\tINTI - Calling create for {} with path {}", name, m.path())

        val newBeanInstance = bs.createInstance(m) // this guy is the node
        bs.injectDictionary(modelElement, newBeanInstance!!, true)

        val factory = (ResourceAwareWrapperFactory(m.name!!, modelRegistry))
        val newBeanKInstanceWrapper: KInstanceWrapper? = factory.wrap(modelElement, newBeanInstance!!, tg!!, bs, ms)
        modelRegistry.register(modelElement, newBeanKInstanceWrapper!!)
    }

    override fun destroy()
    {
    }
}