package com.ebay.kleinanzeigen.imagepicker.drag_and_drop;

public interface SpanVariableGridView$DragAndDropListener {
    boolean isDragAndDropEnabled(int i);

    void onDragItem(int i);

    void onDraggingItem(int i, int i2);

    void onDropItem(int i, int i2);

    void onEdgeXLeft();

    void onEdgeXReached(boolean z);

    void onRemoveItem(int i);
}
