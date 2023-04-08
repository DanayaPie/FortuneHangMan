import Category from "./Category";

function Categories(props) {

    return (
        <div className='hangmanBox'>
            {props.categories.map((category) => (
                <Category
                    category={category}
                    key={category}
                    onCategoryPicked={props.onCategoryPicked}
                />
            ))}
        </div>
    );
}


export default Categories;