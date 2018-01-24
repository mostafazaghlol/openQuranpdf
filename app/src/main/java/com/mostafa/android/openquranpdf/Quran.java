package com.mostafa.android.openquranpdf;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.shockwave.pdfium.PdfDocument;

import java.util.List;

/**
 * Created by mostafa on 1/24/18.
 */

public class Quran extends Fragment implements OnPageChangeListener,OnLoadCompleteListener {
    private static final String TAG = Quran.class.getSimpleName();
    public static final String SAMPLE_FILE = "quran.pdf";
    PDFView pdfView;
    Integer pageNumber = 0;
    String pdfFileName;


    @Override
    public void loadComplete(int nbPages) {
        PdfDocument.Meta meta = pdfView.getDocumentMeta();
        printBookmarksTree(pdfView.getTableOfContents(), "-");

    }
    public void printBookmarksTree(List<PdfDocument.Bookmark> tree, String sep) {
        for (PdfDocument.Bookmark b : tree) {

            Log.e(TAG, String.format("%s %s, p %d", sep, b.getTitle(), b.getPageIdx()));

            if (b.hasChildren()) {
                printBookmarksTree(b.getChildren(), sep + "-");
            }
        }
    }

            @Override
    public void onPageChanged(int page, int pageCount) {
        pageNumber=page;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
          return inflater.inflate(R.layout.pdf_view,container,false);
}

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle("القرأن الكريم ");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        pdfView= (PDFView) getView().findViewById(R.id.pdfView);
        displayFromAsset(SAMPLE_FILE);

    }

    private void displayFromAsset(String sampleFile) {
        pdfFileName=sampleFile;

        pdfView.fromAsset(SAMPLE_FILE).defaultPage(pageNumber).enableSwipe(true)
                .swipeHorizontal(false).onPageChange(this).enableAnnotationRendering(true)
                .onLoad(this).scrollHandle(new DefaultScrollHandle(getActivity())).load();
    }
}
