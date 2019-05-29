package presenter;

import java.util.List;

import bean.DatasBean;
import model.ModelImpl;
import view.ICallback;
import view.IView;

public class PresenterImpl implements IPresenter{
    private final ModelImpl model;
    IView view;

    public PresenterImpl(IView view) {
        this.view = view;
        model = new ModelImpl();
    }

    @Override
    public void updataData() {
        model.updataData(new ICallback() {
            @Override
            public void updataSuccess(List<DatasBean> datasBeans) {
                view.updataSuccess(datasBeans);
            }

            @Override
            public void updataError(String error) {
                view.updataError(error);
            }
        });
    }
}
