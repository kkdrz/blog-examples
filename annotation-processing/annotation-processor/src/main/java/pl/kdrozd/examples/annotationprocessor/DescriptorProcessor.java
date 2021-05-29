package pl.kdrozd.examples.annotationprocessor;

import com.google.auto.service.AutoService;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.tools.FileObject;
import javax.tools.StandardLocation;
import java.io.Writer;
import java.util.Set;
import java.util.stream.Collectors;

@SupportedAnnotationTypes({
        "pl.kdrozd.examples.annotationprocessor.Handler",
        "pl.kdrozd.examples.annotationprocessor.SessionField"
})
@SupportedSourceVersion(SourceVersion.RELEASE_11)
@AutoService(Processor.class)
public class DescriptorProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        Set<? extends Element> annotatedHandlers = roundEnvironment.getElementsAnnotatedWith(Handler.class);

        String handlersXml = annotatedHandlers.stream()
                .map(this::generateHandlerXml)
                .collect(Collectors.joining());

        StringBuilder finalDescriptor = new StringBuilder()
                .append("<?xml version=\"1.0\" encoding=\"utf-8\" ?>")
                .append("\n<deployable-unit>\n")
                .append(handlersXml)
                .append("\n</deployable-unit>\n");

        try {
            FileObject file = processingEnv.getFiler().createResource(StandardLocation.SOURCE_OUTPUT, "resources", "descriptor.xml");
            Writer writer = file.openWriter();
            writer.write(finalDescriptor.toString());
            writer.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    private String generateHandlerXml(Element handler) {

        String handlerPackage = ((PackageElement) handler.getEnclosingElement()).getQualifiedName().toString();
        String fullHandlerClassName = handlerPackage + "." + handler.getSimpleName().toString();

        StringBuilder handlerXml = new StringBuilder();

        handlerXml.append("\n\t<handler>")
                .append("\n\t\t<class>").append(fullHandlerClassName).append("</class>")
                .append("\n\t\t<session-fields>");

        // append fields
        handler.getEnclosedElements().stream()
                .filter(element -> element.getAnnotation(SessionField.class) != null)
                .map(this::getFieldName)
                .forEach(fieldName ->
                        handlerXml.append("\n\t\t\t<field>").append(fieldName).append("</field>")
                );

        handlerXml.append("\n\t\t</session-fields>")
                .append("\n\t</handler>\n");

        return handlerXml.toString();
    }

    private String getFieldName(Element element) {
        String methodName = element.getSimpleName().toString(); // setMyField
        String fieldName = methodName.substring(3); // remove 'set' = MyField
        return Character.toLowerCase(fieldName.charAt(0)) + fieldName.substring(1); //lowercase first letter = myField
    }

}
