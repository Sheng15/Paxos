package shared;

import process.views.App;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public interface RequestType extends Serializable {
    Result executeRequest(ObjectInputStream is, ObjectOutputStream os, App manager);
}
